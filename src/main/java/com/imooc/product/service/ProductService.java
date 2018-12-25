package com.imooc.product.service;

import com.imooc.product.dataobject.ProductInfo;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {

    List<ProductInfo> findUpAll();
}
