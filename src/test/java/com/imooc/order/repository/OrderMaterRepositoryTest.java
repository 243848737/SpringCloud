package com.imooc.order.repository;

import com.imooc.order.OrderApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.Assert.*;

/**
 * Created by：白鹏
 * 2018/12/26 16:57
 */
@Component
public class OrderMaterRepositoryTest extends OrderApplicationTests {

    @Autowired
    private OrderMaterRepository orderMaterRepository;

    private void testSave(){
    }
}