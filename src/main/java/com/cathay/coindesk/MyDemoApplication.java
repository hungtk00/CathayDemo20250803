package com.cathay.coindesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
//@ComponentScan(value = "com.service.**")
public class MyDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyDemoApplication.class, args);
	}

}
