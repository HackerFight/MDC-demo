package com.qiuguan.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiuguan
 * @date 2023/11/28 22:02:35  星期二
 */
@Slf4j
@RestController
@RequestMapping("/ms/http/")
public class HttpController {

    @GetMapping("/v1")
    public String restTemplate() {
        log.info("RestTemplate 调用检查traceId是否一致。。。。。。");
        return "RestTemplate 调用检查traceId是否一致";
    }


    @GetMapping("/v2")
    public String webClient() {
        log.info("WebClient 调用检查traceId是否一致。。。。。。");
        return "WebClient 调用检查traceId是否一致";
    }
}
