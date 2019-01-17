package com.imooc.config.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * 拦截器 拦截/bus-refresh 解决webhooks访问/actuator/bus-refresh 400错误
 * Created By 白鹏
 * 2019/1/1716:39
 */
@Component
public class ConfigFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;

        String url = new String(httpServletRequest.getRequestURI());

        //只过滤/actuator/bus-refresh请求
        if (!url.endsWith("/bus-refresh")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //获取原始的body
        String body = ReadAsChars(httpServletRequest);


        //使用HttpServletRequest包装原始请求达到修改post请求中body内容的目的
        ConfigRequestWrapper requestWrapper = new ConfigRequestWrapper(httpServletRequest);

        filterChain.doFilter(requestWrapper, servletResponse);

    }


    /**
     * 获取request中的Body
     * @param request
     * @return
     */
    public String ReadAsChars(HttpServletRequest request)
    {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try
        {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null)
            {
                sb.append(str);
            }
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
