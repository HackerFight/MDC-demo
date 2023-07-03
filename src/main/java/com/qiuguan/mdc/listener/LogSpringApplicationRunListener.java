package com.qiuguan.mdc.listener;

import com.qiuguan.mdc.utils.MDCUtils;
import org.slf4j.MDC;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.time.Duration;

import static com.qiuguan.mdc.utils.MDCUtils.TRACE;

/**
 * @author fu yuan hui
 * @date 2023-07-03 16:46:49 Monday
 *
 * springboot容器启动时就添加上MDC标识，看控制台
 * 简单演示
 *
 * @see org.springframework.boot.context.event.EventPublishingRunListener
 */
public class LogSpringApplicationRunListener implements SpringApplicationRunListener {

    private final SpringApplication springApplication;

    private final String[] args;

    public LogSpringApplicationRunListener(SpringApplication springApplication,String[] args) {
        this.springApplication = springApplication;
        this.args = args;
    }

    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        MDC.put(TRACE, MDCUtils.generateTraceId());
    }

    @Override
    public void started(ConfigurableApplicationContext context, Duration timeTaken) {
        MDC.remove(TRACE);
    }
}
