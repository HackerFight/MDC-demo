package com.qiuguan.http.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author qiuguan
 * @date 2023/11/28 21:57:38  星期二
 */
@RestController
public class RestTemplateController {

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/rest/http")
    public String rest() {

        return "";
    }
}
