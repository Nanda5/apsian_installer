package com.appsian;

import java.nio.charset.StandardCharsets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@SpringBootApplication
@Configuration
public class AppsianAppInstaller1Application {

	public static void main(String[] args) {
		SpringApplication.run(AppsianAppInstaller1Application.class, args);
	}
	@Bean
	public ThymeleafViewResolver viewResolver(SpringTemplateEngine templateEngine){
	    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
	    viewResolver.setContentType("application/json");
	    viewResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
	    viewResolver.setOrder(1);
	    viewResolver.setViewNames(new String[] {"*.json"});
	    viewResolver.setTemplateEngine(templateEngine);
	    return viewResolver;
	}
}
