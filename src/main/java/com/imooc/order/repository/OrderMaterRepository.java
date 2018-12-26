package com.imooc.order.repository;

import com.imooc.order.dataobject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMaterRepository extends JpaRepository<OrderMaster,String> {


}
