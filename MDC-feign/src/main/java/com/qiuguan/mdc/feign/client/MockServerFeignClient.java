package com.qiuguan.mdc.feign.client;

import com.qiuguan.mdc.feign.config.LocalFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author qiuguan
 * @date 2023/07/03 23:34:26  星期一
 *
 * 这里的 {@link LocalFeignConfiguration} 是局部的feign拦截器，只对当前feign调用生效。
 * 而
 * @see com.qiuguan.mdc.feign.config.FeignBindTraceConfig
 * @see com.qiuguan.mdc.feign.config.OpenFeignConfig
 * 他俩是全局的feign拦截器，所有feign调用都会生效。
 */
@FeignClient(
        name = "mockServerClient",
        url = "http://localhost:8848",
        configuration = LocalFeignConfiguration.class
)
public interface MockServerFeignClient {

    @GetMapping("/ms/feign/test")
    String test();
}
