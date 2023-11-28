package com.qiuguan.mdc.feign.config;

import com.qiuguan.mdc.feign.filter.TraceIdInterceptor;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author qiuguan
 * @date 2023/11/28 22:21:41  星期二
 */
@EnableFeignClients(basePackages = "com.qiuguan.mdc.feign.client")
@ServletComponentScan(basePackages = "com.qiuguan.mdc.feign.filter")
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(traceIdHandlerInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public HandlerInterceptor traceIdHandlerInterceptor() {
        return new TraceIdInterceptor();
    }
}
