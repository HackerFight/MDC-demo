package com.qiuguan.mdc.feign.filter;

import com.qiuguan.mdc.common.utils.MDCUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;

import static com.qiuguan.mdc.common.utils.MDCUtils.TRACE;

/**
 * @author qiuguan
 * @date 2023/11/28 22:18:05  星期二
 *
 *
 *
 * 请求 ---> 网关 ---过滤器 ---拦截器(preHandle方法) --- AOP --- 全局异常
 *  ^                                                           |
 *  |                                                           |
 *  —————————————————————拦截器(afterCompletion方法)<—————————————-
 *
 *
 * 我习惯用 {@link org.springframework.web.servlet.HandlerInterceptor} 接口来设置和移除trace_id
 * 在prehandle()方法中设置traceId, 在afterCompletion()方法中移除traceId
 *
 * @see TraceIdInterceptor
 */
@Slf4j
//@WebFilter(filterName = "traceLogFilter", urlPatterns = "/*")
public class TraceIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("【过滤器22】开始执行请求===================");
        MDC.put(TRACE, MDCUtils.generateTraceId());
        //request header 里面没有
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
