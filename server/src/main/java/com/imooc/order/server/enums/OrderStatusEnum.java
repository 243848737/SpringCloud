package com.imooc.order.server.enums;

import lombok.Getter;

/**
 * Created by：白鹏
 * 2018/12/26 16:49
 */
@Getter
public enum OrderStatusEnum {
    NEW(0,"新订单"),
    FINISHED(1,"完结"),
    CANCEL(3,"取消");

    private int code;

    private String message;

    OrderStatusEnum(int code, String message){
        this.code=code;
        this.message=message;
    }
}
