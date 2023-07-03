package com.qiuguan.mdc.utils;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

import static com.qiuguan.mdc.utils.MDCUtils.TRACE;

/**
 * @author qiuguan
 * 多线程情况下MDC丢失trace_id
 */
public class ThreadMdcUtil {

    public static void setTraceIdIfAbsent() {
        if (MDC.get(TRACE) == null) {
            MDC.put(TRACE, MDCUtils.generateTraceId());
        }
    }

    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }

            setTraceIdIfAbsent();

            try {
                return callable.call();
            } finally {
                MDC.clear();
            }

        };
    }

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}
