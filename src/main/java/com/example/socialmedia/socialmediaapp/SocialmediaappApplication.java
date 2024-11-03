package com.example.socialmedia.socialmediaapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
// import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
// exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class}

@SpringBootApplication
public class SocialmediaappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialmediaappApplication.class, args);
	}

}
