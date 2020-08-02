package com.test.xmonitorcenter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class XMonitorCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(XMonitorCenterApplication.class, args);
        log.info("x-monitor-center running.......");
//        log.info("我来试下热部署");
    }

}
