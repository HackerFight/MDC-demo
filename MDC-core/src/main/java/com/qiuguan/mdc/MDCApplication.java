package com.qiuguan.mdc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author fu yuan hui
 * @date 2023-07-03 15:17:16 Monday
 *
 */
@EnableFeignClients
@SpringBootApplication
public class MDCApplication {

    public static void main(String[] args) {
        SpringApplication.run(MDCApplication.class, args);
    }
}
