package com.imooc.product.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imooc.product.dataobject.ProductInfo;
import lombok.Data;

import java.util.List;

@Data
public class ProductVO {

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    List<ProductInfoVO> productInfoVOList;
}
