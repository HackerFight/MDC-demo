package com.qiuguan.mdc.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.UUID;

import static com.qiuguan.mdc.utils.MDCUtils.TRACE;

/**
 * @author fu yuan hui
 * @date 2023-07-03 15:39:03 Monday
 */
public class ThreadDemo implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MDCDemo.class);

    @Override
    public void run() {

        MDC.put(TRACE, UUID.randomUUID().toString());
        logger.info("开始调用服务进行业务处理");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            logger.info(e.getMessage());
        }
        logger.info("服务处理完毕，可以释放空间了，避免内存泄露");
        MDC.remove(TRACE);

    }
}
