package com.travels.rajbus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class RajbusApplication {

	public static void main(String[] args) {
		SpringApplication.run(RajbusApplication.class, args);
	}

}
