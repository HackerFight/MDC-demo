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
 *
 * 如果是多模块，拦截器一般写在模块的入口处，但是像trace_id 这种，应该是所有模块通用的，所以
 * 最好是在通用模块里面写一个AOP,保证所有模块都可以设置trace_id.
 *
 * 或者写在网关里也可以，然后网关将请求下发到具体的服务中，具体的服务通过拦截器获取trace_id,如果获取到就直接
 * 写到MDC中，如果获取不到，就自己生成一个放到MDC中。
 *
 * 参考文档：
 * https://www.cnblogs.com/jiage/p/14388275.html
 * https://blog.csdn.net/Ellis_li/article/details/127083428
 *
 *
 * 我先写到了拦截器中并测试通过了，所以我先从配置类中移除这个拦截器，然后使用AOP的方式进行测试
 *
 * @see com.qiuguan.mdc.aop.MdcAop
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
