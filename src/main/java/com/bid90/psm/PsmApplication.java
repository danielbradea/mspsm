package com.bid90.psm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestClient;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class PsmApplication {

	public static void main(String[] args) {
		SpringApplication.run(PsmApplication.class, args);
	}

	@Bean
	RestClient restClient(){
		return RestClient.create();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
