package com.imooc.order.server.client;

import com.imooc.order.server.dataobject.ProductInfo;
import com.imooc.order.server.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by：白鹏
 * 2019/1/3 16:41
 */
@FeignClient(name = "product")
public interface ProductClient {

    @GetMapping("/msg")
    String productMst();

    @PostMapping("/product/listForOrder")
    List<ProductInfo> listForOrder(@RequestBody List<String> productIdList);


    @PostMapping("/product/decreaseStock")
     void decreaseStock(@RequestBody List<CartDTO> cartDTOList);

}
