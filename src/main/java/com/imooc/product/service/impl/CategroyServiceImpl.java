package com.imooc.product.service.impl;

import com.imooc.product.dataobject.ProductCategory;
import com.imooc.product.repository.ProductCategoryRepository;
import com.imooc.product.service.CategroyService;
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
