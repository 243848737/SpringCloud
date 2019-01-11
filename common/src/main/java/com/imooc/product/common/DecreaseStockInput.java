package com.imooc.product.common;

import lombok.Data;

/**
 * Created By 白鹏
 * 2019/1/1117:33
 */
@Data
public class DecreaseStockInput {
    /**
     * 商品编号
     */
    private String productId;

    /**
     * 库存
     */
    private Integer productQuantity;

    public DecreaseStockInput(){}

    public DecreaseStockInput(String productId,Integer productQuantity){
        this.productId=productId;
        this.productQuantity=productQuantity;
    }
}
