package com.qiuguan.mdc.filter;

import com.qiuguan.mdc.utils.MDCUtils;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.qiuguan.mdc.utils.MDCUtils.TRACE;

/**
 * @author qiuguan
 * @date 2023/07/04 23:15:15  星期二
 */
@WebFilter("/*")
public class TraceLogRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("trace log filter.........");
        //MDC.put(TRACE, MDCUtils.generateTraceId());
        filterChain.doFilter(request, response);
    }
}
