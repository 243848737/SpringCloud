package com.imooc.product.service.impl;

import com.imooc.product.DTO.CartDTO;
import com.imooc.product.dataobject.ProductInfo;
import com.imooc.product.enums.ProductStatusEnum;
import com.imooc.product.enums.ResutlEnum;
import com.imooc.product.exception.ProductException;
import com.imooc.product.repository.ProductInfoRepository;
import com.imooc.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findList(List<String> productIdList) {
        return productInfoRepository.findByProductIdIn(productIdList);
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
            Optional<ProductInfo> productInfoOptional=productInfoRepository.findById(cartDTO.getProductId());

            //判断商品是否存在
            if (!productInfoOptional.isPresent()){
                throw new ProductException(ResutlEnum.PRODUCT_NOT_EXIST);
            }


            ProductInfo productInfo=productInfoOptional.get();

            //判断商品库存是否足够
            Integer resutl=productInfo.getProductStock()-cartDTO.getProductQuantity();
            if (resutl<0){
                throw new ProductException(ResutlEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(resutl);
            productInfoRepository.save(productInfo);
        }
    }
}
