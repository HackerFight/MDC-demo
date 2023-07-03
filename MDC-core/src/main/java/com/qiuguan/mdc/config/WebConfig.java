package com.qiuguan.mdc.config;

import com.qiuguan.mdc.interceptors.MdcLogInterceptor;
import com.qiuguan.mdc.threadpool.ThreadPoolExecutorMdcWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author fu yuan hui
 * @date 2023-07-03 16:23:40 Monday
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(handlerInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public HandlerInterceptor handlerInterceptor(){
        return new MdcLogInterceptor();
    }

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(){
        return new ThreadPoolExecutorMdcWrapper(5, 10,30L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1024));
    }

}
