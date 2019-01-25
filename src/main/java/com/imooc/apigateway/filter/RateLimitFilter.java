package com.imooc.apigateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.imooc.apigateway.exception.RateLimitException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;

/**
 * 限流
 * Created By 白鹏
 * 2019/1/2415:17
 */
@Component
public class RateLimitFilter extends ZuulFilter {

    //创建google令牌桶限流组件，每隔一秒放100个令牌
    private static final RateLimiter RATE_LIMITER=RateLimiter.create(100);

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {

        return SERVLET_DETECTION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //去取令牌 如果没有取到 那么抛个异常
        if (!RATE_LIMITER.tryAcquire()){
           throw new RateLimitException();
        }
        return null;
    }
}
