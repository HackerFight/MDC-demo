package com.qiuguan.http.interceptor;

import com.qiuguan.mdc.common.utils.MDCUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qiuguan
 * @date 2023/12/02 00:03:52  星期六
 */
public class TraceInterceptor implements HandlerInterceptor {


    /**
     * 正常来说，应该先判断request中是否有trace_id,因为有可能是从其他地方(模块）设置好请求过来的，但我这里不是，我就是
     * 测试从这里发起请求，经过http到MDC-server模块。
     *
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put(MDCUtils.TRACE, MDCUtils.generateTraceId());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(MDCUtils.TRACE);
    }
}
