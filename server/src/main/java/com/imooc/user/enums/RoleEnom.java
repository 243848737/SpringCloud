package com.imooc.user.enums;

import lombok.Getter;

/**
 * Created by：白鹏
 * 2019/1/24 16:57
 */
@Getter
public enum RoleEnom {
    buyer(1,"买家"),
    seller(2,"卖家");

    private Integer code;
    private String message;

    RoleEnom(Integer code,String message){
        this.code=code;
        this.message=message;
    }
}
