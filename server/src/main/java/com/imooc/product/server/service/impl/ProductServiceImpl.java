package com.imooc.product.server.service.impl;


import com.imooc.product.common.DecreaseStockInput;
import com.imooc.product.common.ProductInfoOutPut;
import com.imooc.product.server.DTO.CartDTO;
import com.imooc.product.server.Utils.JsonUtil;
import com.imooc.product.server.dataobject.ProductInfo;
import com.imooc.product.server.enums.ProductStatusEnum;
import com.imooc.product.server.enums.ResutlEnum;
import com.imooc.product.server.exception.ProductException;
import com.imooc.product.server.repository.ProductInfoRepository;
import com.imooc.product.server.service.ProductService;
import com.rabbitmq.tools.json.JSONUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfoOutPut> findList(List<String> productIdList) {
        return productInfoRepository.findByProductIdIn(productIdList).stream()
                .map(e -> {
                    ProductInfoOutPut outPut=new ProductInfoOutPut();
                    BeanUtils.copyProperties(e,outPut);
                    return outPut;
                }).collect(Collectors.toList());
    }

    @Override
    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {
        List<ProductInfo> list=decreaseStockProcess(decreaseStockInputList);

        //List<ProductInfo> 转 List<ProductInfoOutPut>
        List<ProductInfoOutPut> outPutList=list.stream().map(productInfo -> {
            ProductInfoOutPut outPut=new ProductInfoOutPut();
            BeanUtils.copyProperties(productInfo,outPut);
            return outPut;
        }).collect(Collectors.toList());

        //发送mq
        amqpTemplate.convertAndSend("productInfo", JsonUtil.toJson(outPutList));

    }

    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> decreaseStockInputList) {

        List<ProductInfo> list=new ArrayList<>();
        for (DecreaseStockInput decreaseStockInput:decreaseStockInputList){
            Optional<ProductInfo> productInfoOptional=productInfoRepository.findById(decreaseStockInput.getProductId());

            //判断商品是否存在
            if (!productInfoOptional.isPresent()){
                throw new ProductException(ResutlEnum.PRODUCT_NOT_EXIST);
            }


            ProductInfo productInfo=productInfoOptional.get();

            //判断商品库存是否足够
            Integer resutl=productInfo.getProductStock()-decreaseStockInput.getProductQuantity();
            if (resutl<0){
                throw new ProductException(ResutlEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(resutl);
            productInfoRepository.save(productInfo);
            list.add(productInfo);
        }
        return list;
    }
}
