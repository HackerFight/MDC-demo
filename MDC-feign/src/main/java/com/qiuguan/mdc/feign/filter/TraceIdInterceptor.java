package com.qiuguan.mdc.feign.filter;

import com.qiuguan.mdc.common.utils.MDCUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qiuguan
 * @date 2023/11/28 22:36:27  星期二
 */
public class TraceIdInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = request.getHeader(MDCUtils.TRACE);
        if (null == traceId) {
            traceId = MDC.get(MDCUtils.TRACE);
        }
        traceId = traceId == null ? MDCUtils.generateTraceId() : traceId;

        MDC.put(MDCUtils.TRACE, traceId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(MDCUtils.TRACE);
    }
}
