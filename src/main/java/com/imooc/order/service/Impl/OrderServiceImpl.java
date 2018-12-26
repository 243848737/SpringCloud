package com.imooc.order.service.Impl;

import com.imooc.order.utils.KeyUtil;
import com.imooc.order.dataobject.OrderMaster;
import com.imooc.order.dto.OrderDTO;
import com.imooc.order.enums.OrderStatusEnum;
import com.imooc.order.enums.PayStatusEnum;
import com.imooc.order.repository.OrderDetailRepository;
import com.imooc.order.repository.OrderMaterRepository;
import com.imooc.order.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 订单实现
 * Created By 白鹏
 * 2018/12/2617:18
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMaterRepository orderMaterRepository;


    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        //TODO 查询商品信息
        //TODO 计算总价格
        //TODO 扣库存

        //订单入库
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setOrderId(KeyUtil.genUniqueKey());
        BeanUtils.copyProperties(orderDTO,orderDTO);
        orderMaster.setOrderAmount(new BigDecimal(5));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaterRepository.save(orderMaster);
        return orderDTO;
    }
}
