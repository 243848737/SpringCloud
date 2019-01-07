package com.imooc.product.exception;

import com.imooc.product.enums.ResutlEnum;

/**
 * 商品异常
 * Created By 白鹏
 * 2019/1/715:22
 */
public class ProductException extends RuntimeException {

    private Integer code;

    public ProductException(Integer code,String message){
        super(message);
        this.code=code;
    }

    public ProductException(ResutlEnum resutlEnum){
        super(resutlEnum.getMessage());
        this.code=resutlEnum.getCode();
    }
}
