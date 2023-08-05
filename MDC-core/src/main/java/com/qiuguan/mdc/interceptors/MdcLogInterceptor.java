package com.qiuguan.mdc.interceptors;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.qiuguan.mdc.common.utils.MDCUtils.TRACE;
import static com.qiuguan.mdc.common.utils.MDCUtils.generateTraceId;

/**
 * @author fu yuan hui
 * @date 2023-07-03 16:06:49 Monday
 *
 *
 * 如果是多模块，拦截器一般写在模块的入口处，但是像trace_id 这种，应该是所有模块通用的，所以
 * 最好是在通用模块里面写一个AOP,保证所有模块都可以设置trace_id.
 * ======大错特错========
 * AOP是在调用Controller方法的时候进行增强，而拦截器是在Controller方法执行前去执行，所以
 * 实际上是拦截器优先执行。。。。。
 *
 * == 我把拦截器，AOP 全部打开，发现确实是拦截器先执行的。。。。。
 *
 * 所以最好是不是在通用层写一个"过滤器"
 *
 * 或者写在网关里也可以，然后网关将请求下发到具体的服务中，具体的服务通过拦截器获取trace_id,如果获取到就直接
 * 写到MDC中，如果获取不到，就自己生成一个放到MDC中。
 *
 * 参考文档：
 * https://www.cnblogs.com/jiage/p/14388275.html
 * https://blog.csdn.net/Ellis_li/article/details/127083428
 *
 *
 *
 *
 * @see com.qiuguan.mdc.aop.MdcAop
 *
 *  请求 =====> 过滤器 ---- 拦截器 ---- AOP
 *  最好每一个中都先判断是否有trace_id, 如果有则直接使用，没有再去生成和传递
 *
 */
@Slf4j
public class MdcLogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("【MVC拦截器】开始执行请求===================");
        String trace = request.getHeader(TRACE);
        if (!StringUtils.hasText(trace)) {
            trace = MDC.get(TRACE);
        }

        if (!StringUtils.hasText(trace)) {
            trace = generateTraceId();
        }

        MDC.put(TRACE, trace);

        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(TRACE);
    }
}
