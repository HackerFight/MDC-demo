package com.qiuguan.mdc.feign.config;

import com.qiuguan.mdc.common.utils.MDCUtils;
import feign.RequestInterceptor;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;
import java.util.Collection;

import static com.qiuguan.mdc.common.utils.MDCUtils.TRACE;


/**
 * @author qiuguan
 * @date 2023/07/03 23:36:07  星期一
 *
 * 局部的feign拦截器
 *
 * 拦截器 {@link FeignBindTraceConfig } 和 {@link OpenFeignConfig } 是全局拦截器，因为他们有
 * {@link org.springframework.context.annotation.Configuration} 注解标注，所有的feign调用时都会执行
 * 而 {@link LocalFeignConfiguration} 是局部的feign拦截器
 *
 * 局部拦截器的优先级 > 全局拦截器
 */
public class LocalFeignConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate ->  {
            Collection<String> collection = requestTemplate.headers().get(TRACE);
            String traceId = null;
            if (CollectionUtils.isEmpty(collection)) {
                traceId = MDC.get(TRACE);
            }

            if (null == traceId) {
                traceId = MDCUtils.generateTraceId();
            }

            requestTemplate.header(TRACE, traceId);
        };
    }
}
