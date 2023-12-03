package com.qiuguan.server.interceptor;

import com.qiuguan.mdc.common.utils.MDCUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qiuguan
 * @date 2023/08/03 00:38:55  星期四
 */
@Slf4j
public class TraceLogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = request.getHeader(MDCUtils.TRACE);
        traceId = traceId == null ? MDC.get(MDCUtils.TRACE) : traceId;

        MDC.put(MDCUtils.TRACE, traceId == null ? MDCUtils.generateTraceId() : traceId);
        return true;
    }
}
