package com.imooc.apigateway.filter;

import com.imooc.apigateway.constant.CookieConstant;
import com.imooc.apigateway.constant.RedisConstant;
import com.imooc.apigateway.utils.CookieUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 权限拦截(区分买家和卖家)
 * Created By 白鹏
 * 2019/1/2410:34
 */
@Component
public class AuthBuyerFilter extends ZuulFilter {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext=RequestContext.getCurrentContext();
        HttpServletRequest request=requestContext.getRequest();
        if ("/order/order/create".equals(request.getServletPath())) {
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {


        /**
         * /order/create 只能买家访问 (cookie里有openid）
         * /product/list 都可以访问
         */
        RequestContext requestContext=RequestContext.getCurrentContext();
        HttpServletRequest request=requestContext.getRequest();

        Cookie cookie=CookieUtil.get(request, CookieConstant.OPENID);
        if(cookie==null || StringUtils.isEmpty(cookie.getValue())){
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }

        return null;
    }
}
