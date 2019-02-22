package com.imooc.order.server.controller;

import com.imooc.product.common.ProductInfoOutPut;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Created By 白鹏
 * 2019/1/2916:20
 */
@RestController
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {



    //超时配置
//    @HystrixCommand(commandProperties =
//        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000"))
//    @HystrixCommand(commandProperties ={
//            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//设置熔断开启
//            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
//            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
//            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")})//设置 错误百分比
    @HystrixCommand
    @GetMapping("/getPorductInfoList")
    public String getProductInfoList(@RequestParam("num")Integer num){
        if (num % 2 == 0 ){
            return "success";
        }

        RestTemplate restTemplate=new RestTemplate();
        return restTemplate.postForObject("http://localhost:8088/product/listForOrder",
                Arrays.asList("157875196366160022"),
                String.class);
        //throw new RuntimeException("发送异常了");
    }

    public String fallback(){
        return "太拥挤,请稍少再试";
    }

    public String defaultFallback(){
        return "";
    }
}
