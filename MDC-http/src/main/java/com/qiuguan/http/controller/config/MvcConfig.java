package com.qiuguan.http.controller.config;

import com.qiuguan.mdc.common.utils.MDCUtils;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

/**
 * @author qiuguan
 * @date 2023/11/29 23:26:45  星期三
 */
@Configuration
public class MvcConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new HttpRequestInterceptor()));

        return restTemplate;
    }

    /**
     * 指定RestTemplate的拦截器
     */
    static class HttpRequestInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

            String traceId = MDC.get(MDCUtils.TRACE);
            if (StringUtils.hasText(traceId)) {
                request.getHeaders().add(MDCUtils.TRACE, traceId);
            } else {
                request.getHeaders().add(MDCUtils.TRACE, MDCUtils.generateTraceId());
            }
            return execution.execute(request, body);

        }
    }
}
