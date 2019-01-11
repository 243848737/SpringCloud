package com.imooc.product.client;

import com.imooc.product.common.DecreaseStockInput;
import com.imooc.product.common.ProductInfoOutPut;
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
    List<ProductInfoOutPut> listForOrder(@RequestBody List<String> productIdList);


    @PostMapping("/product/decreaseStock")
     void decreaseStock(@RequestBody List<DecreaseStockInput> cartDTOList);

}
