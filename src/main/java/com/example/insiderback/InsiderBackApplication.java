package com.example.insiderback;

import com.example.insiderback.common.security.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class InsiderBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsiderBackApplication.class, args);
	}

}
