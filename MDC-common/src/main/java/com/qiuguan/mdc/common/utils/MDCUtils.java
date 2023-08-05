package com.qiuguan.mdc.common.utils;

import org.slf4j.MDC;

import java.util.UUID;

/**
 * @author fu yuan hui
 * @date 2023-07-03 15:39:48 Monday
 */
public abstract class MDCUtils {

    public static final String TRACE = "trace_id";

    public static String getTraceId() {
        String traceId = MDC.get(TRACE);
        return traceId == null ? "" : traceId;
    }

    public static String generateTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
