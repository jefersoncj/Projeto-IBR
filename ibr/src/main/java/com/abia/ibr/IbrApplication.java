package com.abia.ibr;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class IbrApplication {

	public static void main(String[] args) {
		SpringApplication.run(IbrApplication.class, args);
	}
	
}