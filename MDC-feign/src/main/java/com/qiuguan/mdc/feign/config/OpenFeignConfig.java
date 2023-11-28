package com.qiuguan.mdc.feign.config;

import com.qiuguan.mdc.common.utils.MDCUtils;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qiuguan
 * @date 2023/07/04 23:07:16  星期二
 *
 *
 * 拦截器 {@link FeignBindTraceConfig } 和 {@link OpenFeignConfig } 是全局拦截器，因为他们有
 * {@link org.springframework.context.annotation.Configuration} 注解标注，所有的feign调用时都会执行
 * 而 {@link LocalFeignConfiguration} 是局部的feign拦截器
 */
@Configuration
public class OpenFeignConfig {

    /**
     * 不能重复注册
     */
    @Bean
    Logger.Level level() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor cdbAuthRequestInterceptor() {
        return new OpenFeignTraceInterceptor();
        //return template -> template.header(MDCUtils.TRACE, MDC.get(MDCUtils.TRACE));
    }


    static class OpenFeignTraceInterceptor implements RequestInterceptor {

        @Override
        public void apply(RequestTemplate requestTemplate) {
            String traceId = MDC.get(MDCUtils.TRACE);
            requestTemplate.header(MDCUtils.TRACE, traceId);
        }
    }
}
