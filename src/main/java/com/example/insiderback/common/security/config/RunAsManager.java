package com.example.insiderback.common.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.intercept.RunAsManagerImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class RunAsManager extends GlobalMethodSecurityConfiguration {
    @Override
    protected org.springframework.security.access.intercept.RunAsManager runAsManager() {
        RunAsManagerImpl runAsManager = new RunAsManagerImpl();
        runAsManager.setKey("myKey");
        return runAsManager;
    }
}
