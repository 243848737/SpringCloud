package com.imooc.product.service;

import com.imooc.product.DTO.CartDTO;
import com.imooc.product.dataobject.ProductInfo;
import org.springframework.stereotype.Service;

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
    List<ProductInfo> findList(List<String> productIdList);


    /**
     * 扣库存
     * @param cartDTOList
     */
    void decreaseStock(List<CartDTO> cartDTOList);
}
