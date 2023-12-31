package com.qiuguan.mdc.feign.controller;

import com.qiuguan.mdc.feign.client.MockServerFeignClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author qiuguan
 * @date 2023/07/03 23:31:25  星期一
 */
@Slf4j
@RestController
@AllArgsConstructor
public class HelloController {

    private final MockServerFeignClient mockServerFeignClient;
    @GetMapping("/feign")
    public String point(){

        log.info("feign调用日志打印.........{}", LocalDateTime.now());
        return this.mockServerFeignClient.test();
    }
}
