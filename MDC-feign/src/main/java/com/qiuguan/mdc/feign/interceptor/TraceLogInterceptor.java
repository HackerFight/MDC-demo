package com.qiuguan.mdc.feign.interceptor;

import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qiuguan
 * @date 2023/08/03 00:38:55  星期四
 */
public class TraceLogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = request.getHeader("trace_id");
        MDC.put("trace_id", traceId);

        return true;
    }
}
