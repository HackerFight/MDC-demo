package com.qiuguan.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiuguan
 * @date 2023/11/28 22:02:22  星期二
 */
@Slf4j
@RestController
@RequestMapping("/ms/")
public class FeignController {

    @GetMapping("/feign/test")
    public String feign() {
        log.info("feign 调用检查traceId是否一致。。。。。。。");
        return "feign 调用成功";
    }
}
