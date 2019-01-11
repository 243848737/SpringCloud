package com.imooc.order.server.repository;

import com.imooc.order.server.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
}
