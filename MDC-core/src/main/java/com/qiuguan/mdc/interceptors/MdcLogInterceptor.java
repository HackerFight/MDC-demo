package com.qiuguan.mdc.interceptors;

import com.qiuguan.mdc.utils.MDCUtils;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.qiuguan.mdc.utils.MDCUtils.TRACE;

/**
 * @author fu yuan hui
 * @date 2023-07-03 16:06:49 Monday
 */
public class MdcLogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle...........");
        String trace = request.getHeader(TRACE);
        if (!StringUtils.hasText(trace)) {
            trace = MDCUtils.generateTraceId();
        }

        MDC.put(TRACE, trace);

        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(TRACE);
    }
}
