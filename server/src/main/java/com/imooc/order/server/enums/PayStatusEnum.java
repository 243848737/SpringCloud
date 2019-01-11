package com.imooc.order.server.enums;

import lombok.Getter;

/**
 * Created by：白鹏
 * 2018/12/26 16:49
 */
@Getter
public enum PayStatusEnum {
    WAIT(0,"待支付"),
    SUCCESS(1,"已支付");

    private int code;

    private String message;

    PayStatusEnum(int code, String message){
        this.code=code;
        this.message=message;
    }
}
