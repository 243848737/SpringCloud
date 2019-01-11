package com.imooc.product.server.service.impl;

import com.imooc.product.server.dataobject.ProductCategory;
import com.imooc.product.server.repository.ProductCategoryRepository;
import com.imooc.product.server.service.CategroyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategroyServiceImpl implements CategroyService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return productCategoryRepository.findByCategoryTypeIn(categoryTypeList);
    }
}
