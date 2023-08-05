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

    /**
     * 强烈推荐看看阿里开源的：TransmittableThreadLocal
     * 文档：https://github.com/HackerFight/transmittable-thread-local#-user-guide
     * @return
     */
    @Bean(name = "traceLogAsyncTaskExecutor")
    public ThreadPoolTaskExecutor traceLogAsyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //重点看这里
        executor.setTaskDecorator(new ContextCopyingDecorator());
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix("TraceLog-Async-Thread-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }

    @Bean(name = "basicThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor basicThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix("Basic-Log-Async-Thread-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }

    /**
     * 参考文档：https://www.jianshu.com/p/d000a0a44956
     */
    static class ContextCopyingDecorator implements TaskDecorator {

        @Override
        public Runnable decorate(Runnable runnable) {
            try {
                RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
                Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
                //SecurityContext securityContext = SecurityContextHolder.getContext();
                return () -> {
                    try {
                        RequestContextHolder.setRequestAttributes(requestAttributes);
                        MDC.setContextMap(copyOfContextMap);
                        //SecurityContextHolder.setContext(securityContext);

                        runnable.run();
                    } finally {
                        RequestContextHolder.resetRequestAttributes();
                        MDC.clear();
                        //SecurityContextHolder.clearContext();
                    }
                };
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }

            return runnable;
        }
    }
}
