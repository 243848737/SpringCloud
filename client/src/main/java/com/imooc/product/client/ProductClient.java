package com.imooc.product.client;

import com.imooc.product.common.DecreaseStockInput;
import com.imooc.product.common.ProductInfoOutPut;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by：白鹏
 * 2019/1/3 16:41
 */
@FeignClient(name = "product",fallback = ProductClient.ProductClientFallback.class)
public interface ProductClient {

    @GetMapping("/msg")
    String productMst();

    @PostMapping("/product/listForOrder")
    List<ProductInfoOutPut> listForOrder(@RequestBody List<String> productIdList);


    @PostMapping("/product/decreaseStock")
     void decreaseStock(@RequestBody List<DecreaseStockInput> cartDTOList);

    @Component
    static class ProductClientFallback implements ProductClient{

        @Override
        public String productMst() {
            return null;
        }

        @Override
        public List<ProductInfoOutPut> listForOrder(List<String> productIdList) {
            return null;
        }

        @Override
        public void decreaseStock(List<DecreaseStockInput> cartDTOList) {

        }
    }
}
