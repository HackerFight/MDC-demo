package com.qiuguan.mdc.aop;

import com.qiuguan.mdc.utils.MDCUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author qiuguan
 * @date 2023/08/02 23:53:03  星期三
 *
 * @see com.qiuguan.mdc.interceptors.MdcLogInterceptor
 */
@Slf4j
@Component
@Aspect
public class MdcAop {

    private final static String TRACE_ID = "trace_id";

    @Autowired
    private HttpServletRequest request;

    @Pointcut("execution(public * com.qiuguan.mdc.controller..*.*(..)) ")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void before(){
        if (request != null && request.getHeader(TRACE_ID) != null) {
            MDC.put(TRACE_ID, request.getHeader(TRACE_ID));
        }else{
            MDC.put(TRACE_ID, MDCUtils.generateTraceId());
        }

        log.info("请求入口.......");
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
