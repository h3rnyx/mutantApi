package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class MutantApplication {
	public static void main(String[] args) {
		SpringApplication.run(MutantApplication.class, args);
	}

	@GetMapping("/")
	public String welcome() {
		return "MutantAPI v1.0";
	}
}