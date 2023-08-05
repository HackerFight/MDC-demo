package com.qiuguan.mdc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author fu yuan hui
 * @date 2023-07-03 15:50:09 Monday
 */
@Service
public class OrderService {

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public String createOrder(){
        logger.info("订单创建成功....【使用的是sl4j的 LoggerFactory.getLogger 打印的日志】");

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                /**
//                 * 前面都没有问题，trace_id在这里将不会在打印。
//                 * 请看下面的线程池
//                 */
//                //logger.info("异步【new Thread】发送积分...【使用的是sl4j的 LoggerFactory.getLogger 打印的日志】");
//            }
//        }, "积分线程").start();

        this.threadPoolExecutor.execute(new Runnable() {

            @Override
            public void run() {
               logger.info("异步【通过线程池】发送积分...【使用的是sl4j的 LoggerFactory.getLogger 打印的日志】。");
            }
        });
        return "100001---" + LocalDateTime.now();
    }
}
