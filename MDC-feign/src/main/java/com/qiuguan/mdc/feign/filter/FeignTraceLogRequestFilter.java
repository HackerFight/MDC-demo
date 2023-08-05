package com.qiuguan.mdc.feign.filter;

import com.qiuguan.mdc.common.utils.MDCUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;


/**
 * @author qiuguan
 * @date 2023/07/04 23:15:15  星期二
 */
@Slf4j
@WebFilter(filterName = "traceLogFilter", urlPatterns = "/*")
public class FeignTraceLogRequestFilter implements Filter {

    /**
     * 在 MDC-core 模块中，调用feign请求来到 MDC-feign模块中,他也会首先来到过滤器中
     * 但是无法是从请求头还是MDC.get(trace_id) 都无法获取trace_id, 只有在拦截器中可以直接获取。【这个值就是我在MDC-core模块中Feign拦截器设置的】
     *
     * 所以feign这种的可能只能是在 消费端的Feign拦截器中设置header, 然后再服务提供端通过MVC拦截器获取
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("filter层请求进来。。。。。");
        String traceId = MDC.get(MDCUtils.TRACE);
        System.out.println("traceId = " + traceId);
        //MDC.put(MDCUtils.TRACE, MDCUtils.generateTraceId());
        //request header 里面没有
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
