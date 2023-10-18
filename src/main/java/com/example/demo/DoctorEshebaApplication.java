package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class DoctorEshebaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoctorEshebaApplication.class, args);
	}
}
