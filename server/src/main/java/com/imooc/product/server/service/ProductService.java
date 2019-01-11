package com.imooc.product.server.service;


import com.imooc.product.common.DecreaseStockInput;
import com.imooc.product.common.ProductInfoOutPut;
import com.imooc.product.server.DTO.CartDTO;
import com.imooc.product.server.dataobject.ProductInfo;

import java.util.List;


public interface ProductService {

    /**
     * 查询所有在驾商品
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 查询商品列表
     * @param productIdList
     * @return
     */
    List<ProductInfoOutPut> findList(List<String> productIdList);


    /**
     * 扣库存
     * @param cartDTOList
     */
    void decreaseStock(List<DecreaseStockInput> cartDTOList);
}
