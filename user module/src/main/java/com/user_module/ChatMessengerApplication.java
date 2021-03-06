package com.user_module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ChatMessengerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatMessengerApplication.class, args);

	}

}
