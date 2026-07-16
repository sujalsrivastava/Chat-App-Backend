package com.Project.backend.Chap_App_Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChapAppBackendApplication {

	public static void main(String[] args) {
		System.out.println("mongo url: " + System.getenv("MONGO_URL"));
		SpringApplication.run(ChapAppBackendApplication.class, args);
	}

}
