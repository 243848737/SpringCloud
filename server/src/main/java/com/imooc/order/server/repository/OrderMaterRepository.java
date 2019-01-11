package com.imooc.order.server.repository;

import com.imooc.order.server.dataobject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMaterRepository extends JpaRepository<OrderMaster,String> {


}
