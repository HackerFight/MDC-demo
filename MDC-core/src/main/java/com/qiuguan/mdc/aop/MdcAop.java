package com.qiuguan.mdc.aop;

import com.qiuguan.mdc.common.utils.MDCUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qiuguan
 * @date 2023/08/02 23:53:03  星期三
 *
 * @see com.qiuguan.mdc.interceptors.MdcLogInterceptor
 *
 * 请求 =====> 过滤器 ---- 拦截器 ---- AOP
 * 最好每一个中都先判断是否有trace_id, 如果有则直接使用，没有再去生成和传递
 */
@Slf4j
@Component
@Aspect
public class MdcAop {

    private final static String TRACE_ID = "trace_id";

    @Pointcut("execution(public * com.qiuguan.mdc.controller..*.*(..)) ")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void before(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        String traceId;
        if ((traceId = request.getHeader(TRACE_ID)) != null) {
            MDC.put(TRACE_ID, request.getHeader(TRACE_ID));
        }
        if (!StringUtils.hasText(traceId)) {
            traceId = MDC.get(TRACE_ID);
        }

        if (!StringUtils.hasText(traceId)) {
            traceId = MDCUtils.generateTraceId();
        }

        MDC.put(TRACE_ID, traceId);
        log.info("【AOP】前置通知开始执行请求===================");
    }

    /**
     * 异常通知是不是也要处理一下 ？？？
     * @param point
     * @param responseData
     */
    @AfterReturning(pointcut = "pointCut()", returning = "responseData")
    public void after(JoinPoint point, Object responseData){
        log.info("请求结束前准备移除trace_id.....");
        MDC.remove(TRACE_ID);
        log.info("请求结束.....");
    }
}
