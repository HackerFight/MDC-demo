package com.qiuguan.http.config;

import com.qiuguan.mdc.common.utils.MDCUtils;
import org.slf4j.MDC;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author qiuguan
 * @date 2023/12/02 00:10:58  星期六
 */
@Configuration
public class WebClientConfiguration {

    @LoadBalanced
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder().filter(traceFilter());
    }

    private ExchangeFilterFunction traceFilter() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            String traceId = MDC.get(MDCUtils.TRACE);
            ClientRequest.Builder requestBuilder = ClientRequest.from(clientRequest)
                    .header(MDCUtils.TRACE, traceId);
            return Mono.just(requestBuilder.build());
        });
    }
}
