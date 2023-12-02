package com.qiuguan.http.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author qiuguan
 * @date 2023/11/28 23:26:59  星期二
 */
@Slf4j
@RestController
@RequestMapping("/http/")
public class WebClientController {

    @Resource
    private WebClient.Builder webClientBuilder;

    private static final String URL = "http://localhost:8848/ms/http/v2";

    @GetMapping("/webClient")
    public String webClient() {

        log.info("准备用WebClient发起请求........");

        Mono<String> stringMono = this.webClientBuilder.build().get()
                .uri(URL)
                .retrieve()
                .bodyToMono(String.class);

        //调用一次block()方法，如果调用2次它会发起两次请求，这说明当调用block()方法时才是真正的发起请求
        String result = stringMono.block();
        log.info("WebClient 请求结束。。。。响应结果：{}", result);

        return "webclient 请求结果: " + result;
    }
}
