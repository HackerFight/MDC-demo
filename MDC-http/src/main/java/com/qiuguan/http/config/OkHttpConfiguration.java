package com.qiuguan.http.config;

import com.qiuguan.mdc.common.utils.MDCUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.Buffer;
import okio.BufferedSource;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author qiuguan
 * @date 2023/12/03 10:35:13  星期日
 */
@Configuration
public class OkHttpConfiguration {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(30, 30, TimeUnit.SECONDS))
                .addInterceptor(new OkHttpRequestTraceInterceptor())
                .build();
    }

    @Slf4j
    static class OkHttpRequestTraceInterceptor implements Interceptor {

        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request request = chain.request();
            Headers.Builder builder = request.headers().newBuilder();
            String traceId = builder.get(MDCUtils.TRACE);
            if (traceId == null) {
                traceId = MDC.get(MDCUtils.TRACE);
            }

            builder.add(MDCUtils.TRACE, traceId);
            request = request.newBuilder().headers(builder.build()).build();

            //请求前后都可以做些动作
            Response response = chain.proceed(request);

            BufferedSource source = response.body().source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.getBuffer();
            String responseBody = buffer.readUtf8();

            return response.newBuilder().body(ResponseBody.create(response.body().contentType(), responseBody)).build();
        }
    }
}
