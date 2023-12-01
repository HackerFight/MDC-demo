package com.qiuguan.http.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author qiuguan
 * @date 2023/11/28 21:57:38  星期二
 */
@Slf4j
@RestController
@RequestMapping("/http/")
public class RestTemplateController {

    @Resource
    private RestTemplate restTemplate;

    private static final String URL = "http://localhost:8848/ms/http/v1";

    @GetMapping("/restTemplate")
    public String rest() {

        log.info("准备用RestTemplate发起请求........");
        ResponseEntity<String> forEntity = this.restTemplate.getForEntity(URL, String.class);
        log.info("RestTemplate响应结果: {}", forEntity);
        return forEntity.toString();
    }
}
