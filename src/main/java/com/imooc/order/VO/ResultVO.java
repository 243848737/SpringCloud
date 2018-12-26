package com.imooc.order.VO;

import lombok.Data;

/**
 * Created by 白鹏
 * 2017-12-10 18:02
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;
}
