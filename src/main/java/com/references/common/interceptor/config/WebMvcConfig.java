package com.references.common.interceptor.config;

import com.references.common.interceptor.ControllerLogInterceptor;
import com.references.common.interceptor.LoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    LoggingInterceptor loggingInterceptor;
    @Autowired
    ControllerLogInterceptor controllerLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(controllerLogInterceptor)
                .addPathPatterns("/**")  // 적용할 URL 패턴 지정
                .excludePathPatterns("/public/**");  // 제외할 URL 패턴 지정
    }
}
