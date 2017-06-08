package org.ibayer.personal.kalah.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Configures Jackson
 * @author ibrahim.bayer
 *
 */
@Configuration
public class JacksonConfiguration {

	/**
	 * Configures object mapper for json serialization & de-serialization
	 * 
	 * @return
	 */
	@Bean
	public ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		return objectMapper;
	}

}
