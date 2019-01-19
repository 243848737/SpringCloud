package com.imooc.order.server.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 接收消息
 * Created By 白鹏
 * 2019/1/1814:45
 */
@Slf4j
@Component
public class MqReceiver {

    //需要手动创建队列 @RabbitListener(queues = "myQueue")
    //自动创建队列 @RabbitListener(queuesToDeclare = @Queue("myQueue"))
    //自动创建<Exchange和Queue绑定
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("myExchange")
    ))
    public void process(String message){
        log.info("message:{}",message);
    }


    /**
     * 数码订单
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "computer",
            value = @Queue("computerOrder")
    ))
    public void computerOrder(String message){
        log.info("computerOrder:{}",message);
    }

    /**
     * 水果订单
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "fruit",
            value = @Queue("fruitOrder")
    ))
    public void fruitOrder(String message){
        log.info("fruitOrder:{}",message);
    }
}
