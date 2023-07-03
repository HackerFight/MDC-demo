## springboot + logback + MDC 实现日志的全链路追踪

## 1. MDC-core 模块
1. 首先看下 `com.qiuguan.mdc.demo` 包下的2个类，里面演示了MDC的基本用法
2. `com.qiuguan.mdc.controller` 包下是web场景下使用MDC
> 1.`MDCController` ---> `OrderService` ---> `异步发送积分`, 前面2步日志可以正常打印MDC的信息，但是`异步发送积分` 中使用 `new Thread()` 方式无法打印MDC信息，但是通过包装后的线程池在发送就可以正常打印MDC信息了。
> 2.`MdcLogInterceptor` 拦截器中指定MDC

3. `LogSpringApplicationRunListener` 容器启动时就打印MDC


## 2. MDC-feign
这个模块我是想演示feign调用的MDC打印。。后面补充



## MDC 存在的问题
1. 子线程打印日志丢失traceId
> 就是我前面提到的`异步发送积分`

2. Http调用丢失traceId
> 这个我没有测试，[参考文档](https://juejin.cn/post/7074461710030995492)

3. feign调用会丢失traceId ?
> 这个是我的联想，但是我想他和上面的本质一样，后面在补充。