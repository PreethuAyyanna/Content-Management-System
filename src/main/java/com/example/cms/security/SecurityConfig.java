package com.example.cms.security;

import org.springdoc.core.properties.SwaggerUiConfigProperties.Csrf;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private CustomUserDetailService UserDetailService;
	
	public SecurityConfig(CustomUserDetailService UserDetailService) {
		super();
		this.UserDetailService = UserDetailService; 
	}

	@Bean
	AuthenticationProvider authenticationProvider()
	{
	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	 provider.setPasswordEncoder(passwordEncoder());
	 provider.setUserDetailsService(UserDetailService);
	 return provider;
	}
	
	@Bean
	PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder(12);
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		return http.csrf(csrf->csrf.disable())
				.authorizeHttpRequests(auth->auth.requestMatchers("/users/register")
						.permitAll()
						.anyRequest().authenticated())
						.formLogin(Customizer.withDefaults())
						.build();
						
	}

	
}
