package com.imooc.order.server.message;

import com.alibaba.fastjson.JSONObject;
import com.imooc.order.server.utils.JsonUtil;
import com.imooc.product.common.ProductInfoOutPut;
import com.sun.org.apache.xerces.internal.dom.PSVIAttrNSImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 接收商品消息
 * Created By 白鹏
 * 2019/1/1916:55
 */
@Slf4j
@Component
public class ProductInfoReceiver {

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    private void process(String message){
        //ProductInfoOutPut outPut=(ProductInfoOutPut) JsonUtil.formJosn(message,ProductInfoOutPut.class);
        ProductInfoOutPut outPut=JSONObject.parseObject(message,ProductInfoOutPut.class);
        log.info("从【{}】接收到消息：{}","productInfo",outPut);
    }
}
