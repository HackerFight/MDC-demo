package com.qiuguan.mdc.config;

import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author qiuguan
 * @date 2023/08/03 00:33:30  星期四
 *
 * 这个是我参考了 https://blog.csdn.net/lavorange/article/details/126087077 文章写到，我没有测试。。。
 *
 * 我觉得这种办法看起来非常好。不知道是否真的有效。
 */
@Configuration
public class ThreadPoolConfig {

    @Bean(name = "traceLogAsyncTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setTaskDecorator(new ContextCopyingDecorator());
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix("SeelAsyncThread-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }

    static class ContextCopyingDecorator implements TaskDecorator {

        @Override
        public Runnable decorate(Runnable runnable) {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
            return () -> {
                try {
                    RequestContextHolder.setRequestAttributes(requestAttributes);
                    MDC.setContextMap(copyOfContextMap);
                } finally {
                    RequestContextHolder.resetRequestAttributes();
                }
            };
        }
    }
}
