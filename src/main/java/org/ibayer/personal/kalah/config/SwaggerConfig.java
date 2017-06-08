package org.ibayer.personal.kalah.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration of swagger is managed via this class.
 * @author ibrahim.bayer
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 * @return 
	 */
	@Bean
	public Docket api() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2); 
		return docket.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET,
						Arrays.asList(
								new ResponseMessageBuilder().code(500).message("500 message")
										.responseModel(new ModelRef("Error")).build(),
								new ResponseMessageBuilder().code(403).message("Forbidden!").build()))
				.select().apis(RequestHandlerSelectors.basePackage("org.ibayer.personal.kalah.api")).build();

	}

}
