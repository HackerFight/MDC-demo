package com.qiuguan.mdc.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.UUID;

import static com.qiuguan.mdc.utils.MDCUtils.TRACE;

/**
 * @author fu yuan hui
 * @date 2023-07-03 15:20:22 Monday
 */
public class MDCDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(MDCDemo.class);

    public static void main(String[] args) {
        MDC.put(TRACE, UUID.randomUUID().toString());
        LOGGER.info("开始调用方法A...");
        LOGGER.info("方法A调用方法B...");
        LOGGER.info("调用完成");

        MDC.remove(TRACE);

        LOGGER.info("trace_id 还存在吗 ？{}", MDC.get(TRACE) != null);

        //多线程测试
        threadDemo();
    }

    private static void threadDemo() {
        System.out.println("========================================");
        new Thread(new ThreadDemo(), "t1").start();
        new Thread(new ThreadDemo(), "t2 ").start();
    }
}
