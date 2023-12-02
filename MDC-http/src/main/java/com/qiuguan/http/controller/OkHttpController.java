package com.qiuguan.http.controller;

import okhttp3.OkHttpClient;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiuguan
 * @date 2023/12/02 15:08:09  星期六
 *
 * 感觉现在开发中直接用OkHttp发起请求的不多，用 {@link org.springframework.web.client.RestTemplate} 基本足够
 */
@RestController
public class OkHttpController {

    private final OkHttpClient okHttpClient = new OkHttpClient();
}
