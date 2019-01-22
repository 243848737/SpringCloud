package com.imooc.order.server.message;

import com.alibaba.fastjson.JSONObject;
import com.imooc.product.common.ProductInfoOutPut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 接收商品消息
 * Created By 白鹏
 * 2019/1/1916:55
 */
@Slf4j
@Component
public class ProductInfoReceiver {

    private static  final String PRODUCT_STOCK_TEMPLATE = "product_stock_%s";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    private void process(String message){
        //ProductInfoOutPut outPut=(ProductInfoOutPut) JsonUtil.formJosn(message,ProductInfoOutPut.class);
        List<ProductInfoOutPut> productInfoOutPutList=JSONObject.parseArray(message,ProductInfoOutPut.class);
        log.info("从【{}】接收到消息：{}","productInfo",productInfoOutPutList);

        //将消息存入Redis
        for (ProductInfoOutPut outPut:productInfoOutPutList) {
            stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE, outPut.getProductId()), outPut.getProductStock().toString());
        }
    }
}
