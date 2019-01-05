package com.imooc.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提供商品服务
 * Created By 白鹏
 * 2019/1/215:04
 */
@RestController
public class ServerController {


    @GetMapping("/msg")
    public String msg(){
        return "this is product msg";
    }

}
