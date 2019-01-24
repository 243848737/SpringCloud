package com.imooc.user.utils;

import java.util.Random;

/**
 * 生成key的util
 * Created By 白鹏
 * 2018/12/2617:33
 */
public class KeyUtil {
    public static synchronized  String genUniqueKey(){
        Random random=new Random();
        Integer number=random.nextInt(900000)+100000;

        return System.currentTimeMillis()+number.toString();
    }

}
