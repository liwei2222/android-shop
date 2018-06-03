package com.app.androidshop;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Slf4j
public class AndroidShopApplication {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        AndroidShopApplication a = new AndroidShopApplication();
        a.logger.info("==============>bootstrap");
        SpringApplication.run(AndroidShopApplication.class, args);
    }
}
