package com.example.demo.repositories;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.demo.repositories")
@EntityScan("com.example.demo.entities")
@Configuration
public class ApplicationTest {

	  public static void main(String[] args) {
	    SpringApplication.run(ApplicationTest.class, args);
	  }

}
