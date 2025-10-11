package com.apigateway.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for web-related settings, including CORS configurations.
 */
@Configuration
public class WebConfig {

	@Value("${client.prot}")
	private String clientProtocol;

	@Value("${client.ip}")
	private String clientIp;

	@Value("${client.port}")
	private int clientPort;

	/**
	 * Configures CORS settings for the application.
	 *
	 * @return a {@link WebMvcConfigurer} that adds CORS mappings
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins(clientProtocol + "://" + clientIp + ":" + clientPort, clientProtocol + "://" + clientIp)
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
						.allowCredentials(true);
			}
		};
	}
}
