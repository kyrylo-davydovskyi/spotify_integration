package com.davydovskyi.spotify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SpotifyAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotifyAnalyzerApplication.class, args);
	}

}
