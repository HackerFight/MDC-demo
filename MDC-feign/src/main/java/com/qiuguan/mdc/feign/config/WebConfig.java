package com.qiuguan.mdc.feign.config;

import com.qiuguan.mdc.feign.interceptor.TraceLogInterceptor;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author fu yuan hui
 * @date 2023-07-03 16:23:40 Monday
 */
@ServletComponentScan(basePackages = "com.qiuguan.mdc.feign.filter")
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(handlerInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public HandlerInterceptor handlerInterceptor(){
        return new TraceLogInterceptor();
    }
}
