package com.imooc.order.server.exception;


import com.imooc.order.server.enums.ResultEnum;

/**
 * Created by 白鹏
 * 2017-12-10 17:27
 */
public class OrderException extends RuntimeException {

    private Integer code;

    public OrderException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public OrderException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
