package com.imooc.apigateway.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 设置CookieUtile
 * Created By 白鹏
 * 2019/1/2417:01
 */
public class CookieUtil {
    public static void set(HttpServletResponse response,
                           String name,
                           String value,
                           int maxAge){
        Cookie cookie=new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);

    }

    public static Cookie get(HttpServletRequest request,String name){
        Cookie[] cookies=request.getCookies();
        if (cookies != null){
            for (Cookie cookie:cookies){
                if (name.equals(cookie.getName())){
                    return cookie;
                }
            }
        }
        return null;
    }
}
