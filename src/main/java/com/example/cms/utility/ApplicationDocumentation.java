package com.example.cms.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class ApplicationDocumentation {
	
	@Bean
	Info info() {
		return new Info().title("Content Management System")
				.description("To create a flexible platform for managing digital content.")
				.version("v1");
	}

	@Bean
	OpenAPI openAPI()
	{
		return new OpenAPI().info(info());

	}

}
