package com.qiuguan.mdc.config;

import com.qiuguan.mdc.utils.MDCUtils;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.print.DocFlavor;

/**
 * @author qiuguan
 * @date 2023/07/04 23:07:16  星期二
 */
@Configuration
public class OpenFeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
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
