package com.selcukmeral.security.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "security.config.jwt")
public class SecurityPropertiesConfiguration {

	private String headerName;

	private String tokenIssuer;
	
	private String tokenAuthoritiesName;
	
	private Integer tokenValidity;
	
	private String tokenKey;
	
}
