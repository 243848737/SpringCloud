package com.imooc.product.server.enums;

import lombok.Getter;

/**
 * Created by：白鹏
 * 2019/1/7 15:25
 */
@Getter
public enum ResutlEnum {

    PRODUCT_NOT_EXIST(1,"商品不存在"),
    PRODUCT_STOCK_ERROR(2,"库存不足");
    private Integer code;
    private String message;

    ResutlEnum(Integer code,String message){
        this.code=code;
        this.message=message;
    }
}
