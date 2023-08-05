package com.qiuguan.mdc.config;

import com.qiuguan.mdc.interceptors.HttpRequestInterceptor;
import com.qiuguan.mdc.interceptors.MdcLogInterceptor;
import com.qiuguan.mdc.threadpool.ThreadPoolExecutorMdcWrapper;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author fu yuan hui
 * @date 2023-07-03 16:23:40 Monday
 *
 * @see ServletComponentScan 扫描标注了
 * {@link javax.servlet.annotation.WebFilter},
 * {@link javax.servlet.annotation.WebServlet},
 * {@link javax.servlet.annotation.WebListener}
 * 注解的组件。或者使用 {@link org.springframework.boot.web.servlet.ServletRegistrationBean} 去配置类中注册
 *
 * 请求 =====> 过滤器 ---- 拦截器 ---- AOP
 * 最好每一个中都先判断是否有trace_id, 如果有则直接使用，没有再去生成和传递
 */
@ServletComponentScan(basePackages = "com.qiuguan.mdc.filter")
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

    //==========================http================================//

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(httpRequestInterceptor()));

        return restTemplate;
    }


    @Bean
    public ClientHttpRequestInterceptor httpRequestInterceptor(){
        return new HttpRequestInterceptor();
    }

}
