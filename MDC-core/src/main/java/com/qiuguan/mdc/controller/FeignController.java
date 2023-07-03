package com.qiuguan.mdc.controller;

import com.qiuguan.mdc.feign.PointFeignClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiuguan
 * @date 2023/07/03 23:44:56  星期一
 */
@Slf4j
@RestController
public class FeignController {

    @Autowired
    private PointFeignClient pointFeignClient;

    @GetMapping("/feign/point")
    public String point(){
        log.info("请求入口日志打印.....");

        String point = this.pointFeignClient.point();

        log.info("请求结束日志打印.......");

        return point;
    }
}
