package com.qiuguan.mdc.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author qiuguan
 * @date 2023/07/03 23:34:26  星期一
 *
 * 这里使用局部的配置类或者全局的配置类都可以
 * @see com.qiuguan.mdc.config.OpenFeignConfig
 *
 *
 * 测试了几次，发现服务提供者的【MDC-feign 模块】 也需要定义拦截器，然后从request 中header中获取trace_id, 然后放到
 * MDC中，这样就打印了。。。。。
 *
 * 还有其他办法可以让服务提供者打印日志吗？  有待考察
 * 1.或者过滤器？
 * 2.或者AOP ?
 *
 * 参考这个文档：https://blog.csdn.net/MadLifeBin/article/details/120445839
 * 它实际上就是给每个服务（服务提供者，服务消费者）都写了一个过滤器，然后在过滤器中操作MDC
 */
@FeignClient(name = "pointClient", url = "http://localhost:9002"/*, configuration = PointFeignConfiguration.class*/)
public interface PointFeignClient {

    @GetMapping("/p/f/point")
    String point();
}
