package com.deportido;

import org.springframework.boot.SpringApplication; 
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication
@EnableScheduling
public class DeportivoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeportivoApplication.class, args);
		System.out.println("Hola mundo");
	}

}
