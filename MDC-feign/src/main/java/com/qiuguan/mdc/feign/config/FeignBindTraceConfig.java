package com.qiuguan.mdc.feign.config;

import com.qiuguan.mdc.common.utils.MDCUtils;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fu yuan hui
 * @date 2023-09-27 16:51:46 Wednesday
 *
 * 拦截器 {@link FeignBindTraceConfig } 和 {@link OpenFeignConfig } 是全局拦截器，因为他们有
 * {@link org.springframework.context.annotation.Configuration} 注解标注，所有的feign调用时都会执行
 * 而 {@link LocalFeignConfiguration} 是局部的feign拦截器
 */

@Configuration
public class FeignBindTraceConfig {


    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor traceLogRequestInterceptor() {
        return new OpenFeignTraceInterceptor();
    }


    static class OpenFeignTraceInterceptor implements RequestInterceptor {

        @Override
        public void apply(RequestTemplate requestTemplate) {
            String traceId = MDC.get(MDCUtils.TRACE);
            requestTemplate.header(MDCUtils.TRACE, traceId);
        }
    }
}
