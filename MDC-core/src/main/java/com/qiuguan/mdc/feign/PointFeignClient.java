package com.qiuguan.mdc.feign;

import com.qiuguan.mdc.config.PointFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author qiuguan
 * @date 2023/07/03 23:34:26  星期一
 */
@FeignClient(name = "pointClient", url = "http://localhost:9002", configuration = PointFeignConfiguration.class)
public interface PointFeignClient {

    @GetMapping("/p/f/point")
    String point();
}
