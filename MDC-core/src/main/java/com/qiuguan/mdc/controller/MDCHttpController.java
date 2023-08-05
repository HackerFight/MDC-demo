package com.qiuguan.mdc.controller;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author qiuguan
 * @date 2023/08/05 15:08:54  星期六
 *
 * http调用常见的有3种
 * @see org.springframework.web.client.RestTemplate
 * @see okhttp3.OkHttpClient
 * @see org.apache.hc.client5.http.impl.classic.HttpClients
 *
 * 因为我这里暂时没有用到，所以就先不写了，以后用到再说吧。
 *
 * 参考文档：https://blog.csdn.net/xibei19921101/article/details/119807301
 */
public class MDCHttpController {

    @GetMapping("/restTemplate")
    public String restTemplate(){

        return "";
    }

    @GetMapping("/okHttp")
    public String okHttp(){
        //OkHttpClient.Builder.
        return null;
    }

    @GetMapping("/httpClient")
    public String httpClient(){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        return null;
    }
}
