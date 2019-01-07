package com.imooc.order.service.Impl;

import com.imooc.order.client.ProductClient;
import com.imooc.order.dataobject.OrderDetail;
import com.imooc.order.dataobject.ProductInfo;
import com.imooc.order.dto.CartDTO;
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
        String orderId=KeyUtil.genUniqueKey();

        //查询商品信息
        List<String> productIdList=orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId).collect(Collectors.toList());

        List<ProductInfo> productInfoList=productClient.listForOrder(productIdList);

        // 计算总价格
        BigDecimal orderAmout=new BigDecimal(BigInteger.ZERO);
        //循环订单
        for (OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            //循环查询出的商品
            for (ProductInfo productInfo:productInfoList){
                //判断订单中商品的id和商品的id是否一样 一样说明买这个商品
                if (productInfo.getProductId().equals(orderDetail.getProductId())){
                    //单价乘数量
                    orderAmout=productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmout);

                    BeanUtils.copyProperties(productInfo,orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());

                    //订单详情入库
                    orderDetailRepository.save(orderDetail);
                }
            }
        }

        // 扣库存
        List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());

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
