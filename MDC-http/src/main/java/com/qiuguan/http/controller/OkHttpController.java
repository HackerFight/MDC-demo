package com.qiuguan.http.controller;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author qiuguan
 * @date 2023/12/02 15:08:09  星期六
 *
 * 感觉现在开发中直接用OkHttp发起请求的不多，用 {@link org.springframework.web.client.RestTemplate} 基本足够
 */
@Slf4j
@RestController
public class OkHttpController {

    @Resource
    private OkHttpClient okHttpClient;

    private static final String URL = "http://localhost:8848/ms/http/v3";


    @GetMapping("/http/okHttp")
    public String okHttp() throws Exception {

        log.info("准备用OkHttp发起请求........");

        Request request = new Request.Builder()
                .get()
                .url(URL)
                .addHeader("token", "123")
                .build();

        Response execute = this.okHttpClient.newCall(request).execute();

        return "okHttp 请求结果: " + execute.body();
    }
}
