package com.imooc.order.server.service;


import com.imooc.order.server.dto.OrderDTO;

/**
 * Created by：白鹏
 * 2018/12/26 17:14
 */
public interface OrderService {

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 完结订单
     * @return
     */
    OrderDTO finsh(String orderId);
}
