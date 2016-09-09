package com.partofa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.partofa")
public class PartofaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PartofaApplication.class, args);
	}
}
