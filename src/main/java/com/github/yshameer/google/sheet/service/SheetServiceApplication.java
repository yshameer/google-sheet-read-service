package com.github.yshameer.google.sheet.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableConfigurationProperties
@ComponentScan(basePackages = "com.github.yshameer.google.sheet.service")
public class SheetServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SheetServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
