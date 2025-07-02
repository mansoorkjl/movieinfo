package com.manchu.movieinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableCaching
@RestController
public class MovieinfoApplication {
	public static void main(String[] args) {
		SpringApplication.run(MovieinfoApplication.class, args);
	}
	@GetMapping("/root")
	public String apiRoot(){
		return "Welcome to the Spring Boot API Coding!";
	}

}
