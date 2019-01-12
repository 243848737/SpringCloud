package com.imooc.order.server.controller;

import com.imooc.order.server.dataobject.ProductInfo;
import com.imooc.order.server.dto.CartDTO;
import com.imooc.product.client.ProductClient;
import com.imooc.product.common.DecreaseStockInput;
import com.imooc.product.common.ProductInfoOutPut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 调用商品服务
 * Created By 白鹏
 * 2019/1/215:08
 */
@RestController
@Slf4j
public class ClientController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

//    @Autowired
//    private RestTemplate restTemplate;

//    @GetMapping("/getProductMsg")
//    public String getProductMsg(){
//        //第一种方式 直接使用RestTemplate 写死URL
////        RestTemplate restTemplate=new RestTemplate();
////        String response=restTemplate.getForObject("http://localhost:8080/msg",String.class);
////        log.info("response:{}",response);
//
//        //第二种方式 利用LoadBalancerClient 通过应用名称获取url 使用RestTemplate 访问
//        ServiceInstance instance=loadBalancerClient.choose("PRODUCT");
//        String url=String.format("http://%s:%s",instance.getHost(),instance.getPort());
//        RestTemplate restTemplate=new RestTemplate();
//        String response=restTemplate.getForObject("http://localhost:8080/msg",String.class);
//        log.info("response:{}",response);
//
//        //第三种 理由@LoadBalanced 可以直接在restTemplatel里直接使用应用名称
////        String response=restTemplate.getForObject("http://PRODUCT/msg",String.class);
////        log.info("response:{}",response);
//        return response;
//    }

    @Autowired
    private ProductClient productClient;

    @GetMapping("/getProductMsg")
    public String getProductMsg(){
        String response=productClient.productMst();
        log.info("response:{}",response);
        return response;
    }

    @GetMapping("/getProductList")
    public String getProductList(){
        List<ProductInfoOutPut> productInfoList=productClient.listForOrder(Arrays.asList("157875196366160022"));
        log.info("productInfoList{}",productInfoList);
        return "ok";
    }

    @GetMapping("/decreaseStock")
    public String decreaseStock(){
        productClient.decreaseStock(Arrays.asList(new DecreaseStockInput("164103465734242707",3)));
        return "ok";
    }
}
