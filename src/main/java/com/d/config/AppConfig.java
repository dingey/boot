package com.d.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
public class AppConfig {
    private static Logger logger = LoggerFactory.getLogger(AppConfig.class);

    static {
        logger.info("配置应用dev环境开始。。。");
        if (System.getenv("db_pwd") == null && System.getProperty("db_pwd") == null) {
            System.setProperty("db_pwd", "root");
        }
    }
}