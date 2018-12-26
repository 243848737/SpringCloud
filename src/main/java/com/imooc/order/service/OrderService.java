package com.imooc.order.service;

import com.imooc.order.dataobject.OrderDetail;
import com.imooc.order.dataobject.ProductInfo;
import com.imooc.order.dto.OrderDTO;

/**
 * Created by：白鹏
 * 2018/12/26 17:14
 */
public interface OrderService {

    OrderDTO create(OrderDTO orderDTO);
}
