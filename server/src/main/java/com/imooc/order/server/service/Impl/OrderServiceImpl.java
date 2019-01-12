package com.imooc.order.server.service.Impl;

import com.imooc.order.server.dataobject.OrderDetail;
import com.imooc.order.server.dataobject.OrderMaster;
import com.imooc.order.server.dto.OrderDTO;
import com.imooc.order.server.enums.OrderStatusEnum;
import com.imooc.order.server.enums.PayStatusEnum;
import com.imooc.order.server.repository.OrderDetailRepository;
import com.imooc.order.server.repository.OrderMaterRepository;
import com.imooc.order.server.service.OrderService;
import com.imooc.order.server.utils.KeyUtil;
import com.imooc.product.client.ProductClient;
import com.imooc.product.common.DecreaseStockInput;
import com.imooc.product.common.ProductInfoOutPut;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private ProductClient productClient;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId= KeyUtil.genUniqueKey();

        //查询商品信息
        List<String> productIdList=orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId).collect(Collectors.toList());

        List<ProductInfoOutPut> productInfoList=productClient.listForOrder(productIdList);

        // 计算总价格
        BigDecimal orderAmout=new BigDecimal(BigInteger.ZERO);
        //循环订单
        for (OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            //循环查询出的商品
            for (ProductInfoOutPut productInfoOutPut:productInfoList){
                //判断订单中商品的id和商品的id是否一样 一样说明买这个商品
                if (productInfoOutPut.getProductId().equals(orderDetail.getProductId())){
                    //单价乘数量
                    orderAmout=productInfoOutPut.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmout);

                    BeanUtils.copyProperties(productInfoOutPut,orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());

                    //订单详情入库
                    orderDetailRepository.save(orderDetail);
                }
            }
        }

        // 扣库存
        List<DecreaseStockInput> cartDTOList=orderDTO.getOrderDetailList().stream()
                .map(e -> new DecreaseStockInput(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());

        productClient.decreaseStock(cartDTOList);

        //订单入库
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmout);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaterRepository.save(orderMaster);
        return orderDTO;
    }
}
