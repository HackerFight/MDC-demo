package com.qiuguan.mdc.interceptors;

import com.qiuguan.mdc.common.utils.MDCUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StringUtils;

import java.io.IOException;

import static com.qiuguan.mdc.common.utils.MDCUtils.TRACE;


/**
 * @author qiuguan
 * @date 2023/07/03 22:51:21  星期一
 */
public class HttpRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        String traceId = MDC.get(TRACE);
        if (StringUtils.hasText(traceId)) {
            request.getHeaders().add(TRACE, traceId);
        } else {
            request.getHeaders().add(TRACE, MDCUtils.generateTraceId());
        }
        return execution.execute(request, body);

    }
}
