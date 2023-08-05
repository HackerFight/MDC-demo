package com.qiuguan.mdc.filter;

import com.qiuguan.mdc.common.utils.MDCUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

import static com.qiuguan.mdc.common.utils.MDCUtils.TRACE;

/**
 * @author qiuguan
 * @date 2023/07/04 23:15:15  星期二
 */
@Slf4j
@WebFilter(filterName = "traceLogFilter", urlPatterns = "/*")
public class TraceLogRequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("【过滤器】开始执行请求===================");
        MDC.put(TRACE, MDCUtils.generateTraceId());
        //request header 里面没有
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
