package com.imooc.order.server.service;


import com.imooc.order.server.dto.OrderDTO;

/**
 * Created by：白鹏
 * 2018/12/26 17:14
 */
public interface OrderService {

    OrderDTO create(OrderDTO orderDTO);
}
