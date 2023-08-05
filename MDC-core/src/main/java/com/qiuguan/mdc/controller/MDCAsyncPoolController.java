package com.qiuguan.mdc.controller;

import com.qiuguan.mdc.config.ThreadPoolConfig;
import com.qiuguan.mdc.threadpool.ThreadPoolExecutorMdcWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author qiuguan
 * @date 2023/08/05 00:18:00  星期六
 *
 * 主要是测试 {@link ThreadPoolConfig#threadPoolTaskExecutor()}
 */
@Slf4j
@RestController
public class MDCAsyncPoolController {

    @Qualifier("traceLogAsyncTaskExecutor")
    @Autowired
    private ThreadPoolTaskExecutor traceLogAsyncTaskExecutor;


    @Qualifier("basicThreadPoolTaskExecutor")
    @Autowired
    private ThreadPoolTaskExecutor basicThreadPoolTaskExecutor;

    /**
     * 使用的是 {@link ThreadPoolExecutorMdcWrapper} 进行包装的线程池
     */
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @GetMapping("/mdc/pool")
    public String asyncTest(){
        log.info("----------> /mdc/pool/ 进入......");

        /**
         * 可以打印日志，但是没有trace_id
         */
        new Thread(() -> log.info("子线程1。。。。。。")).start();

        /**
         * System和log都输出，且有trace_id, 请看线程池配置 {@link ThreadPoolConfig#traceLogAsyncTaskExecutor()}
         */
        traceLogAsyncTaskExecutor.execute(() -> {
            System.out.println("子线程2.。。。。。。");
            log.info("子线程2。。。。。。");
        });

        /**
         * System和log都有输出，但是没有trace_id, 这是正常的
         */
        basicThreadPoolTaskExecutor.execute(() -> {
            System.out.println("子线程3.。。。。。。");
            log.info("子线程3。。。。。。");
        });

        /**
         * 打印日志，有trace_id
         */
        threadPoolExecutor.execute(() -> log.info("子线程4。。。。。。"));

        return "hello success===" + LocalDateTime.now();
    }
}
