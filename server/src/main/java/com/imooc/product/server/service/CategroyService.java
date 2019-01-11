package com.imooc.product.server.service;

import com.imooc.product.server.dataobject.ProductCategory;

import java.util.List;

public interface CategroyService {
    List<ProductCategory> findByCategoryTypeIn(List<Integer> CategoryTypeList);
}
