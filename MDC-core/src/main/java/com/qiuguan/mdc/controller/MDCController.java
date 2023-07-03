package com.qiuguan.mdc.controller;

import com.qiuguan.mdc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fu yuan hui
 * @date 2023-07-03 15:49:22 Monday
 */
@Slf4j
@RestController
public class MDCController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/mdc")
    public String test(){
        log.info("用户准备创建订单....【使用的是lombok的@Slf4j注解打印的日志】");

        String order = this.orderService.createOrder();

        log.info("订单创建结束，返回给用户....【使用的是lombok的@Slf4j注解打印的日志】");

        return order;
    }
}
