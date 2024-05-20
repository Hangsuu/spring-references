package com.references.common.interceptor.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppConfig implements ApplicationContextAware {
    // AesServiceImpl을 직접 생성해서 사용할 경우(static 등) env변수를 넣어주기 위한 config

    private static Environment environment;
    private static ApplicationContext applicationContext;

    @Autowired
    private Environment env;

    @PostConstruct
    public void init() {
        environment = env;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        AppConfig.applicationContext = applicationContext;
    }

    public static String getKey() {
        return environment.getProperty("aes.key");
    }

    public static String getAlg() {
        return environment.getProperty("aes.alg");
    }

    public static Environment getEnvironment() {
        return environment;
    }
}
