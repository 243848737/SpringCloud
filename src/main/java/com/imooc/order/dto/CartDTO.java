package com.imooc.order.dto;

import lombok.Data;
import net.bytebuddy.asm.Advice;

/**
 * 扣库存 dto
 * Created By 白鹏
 * 2019/1/714:51
 */
@Data
public class CartDTO {

    /**
     * 商品编号
     */
    private String productId;

    /**
     * 库存
     */
    private Integer productQuantity;

    public CartDTO(){}

    public CartDTO(String productId,Integer productQuantity){
        this.productId=productId;
        this.productQuantity=productQuantity;
    }
}
