package com.esi.project.buildit.planthire;

import com.esi.project.buildit.planthire.common.security.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class PlantHireApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlantHireApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtConfig jwtConfig(){
		return new JwtConfig();
	}

}
