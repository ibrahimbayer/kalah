package org.ibayer.personal.kalah;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Kalah Game application.
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@EnableCaching
public class KalahApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(KalahApplication.class, args);
	}
	
}
