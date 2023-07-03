package com.qiuguan.mdc.config;

import com.qiuguan.mdc.utils.MDCUtils;
import feign.RequestInterceptor;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;

import static com.qiuguan.mdc.utils.MDCUtils.TRACE;

/**
 * @author qiuguan
 * @date 2023/07/03 23:36:07  星期一
 */
public class PointFeignConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate ->  {
            Collection<String> collection = requestTemplate.headers().get(TRACE);
            String traceId = null;
            if (CollectionUtils.isEmpty(collection)) {
                MDC.put(TRACE, traceId = MDCUtils.generateTraceId());
            } else {
                MDC.put(TRACE, new ArrayList<>(collection).get(0));
            }

            //requestTemplate.header(TRACE, traceId);
        };
    }
}
