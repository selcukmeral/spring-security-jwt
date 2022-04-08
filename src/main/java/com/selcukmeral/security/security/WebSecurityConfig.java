package com.selcukmeral.security.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;


/**
* @author selcukmeral
*/

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    
    private final JwtFilter jwtFilter;
    
    private final CustomAuthenticationProvider customAuthenticationProvider;
    
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		
		final CorsConfiguration corsConfiguration = new CorsConfiguration();
	    corsConfiguration.setAllowedHeaders(List.of("X-Auth-Token", "Cache-Control", "Content-Type","X-CSRF-TOKEN"));
	    corsConfiguration.setAllowedOrigins(List.of("chrome-extension://aicmkgpgakddgnaphhhpliifpcfhicfo"));
	    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
	    corsConfiguration.setAllowCredentials(true);
	    corsConfiguration.setExposedHeaders(List.of("X-Auth-Token","X-CSRF-TOKEN"));
		    
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}
    
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/login-service/**");
    }    
	  
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	
        // Enable CORS 
        http.cors();
        
        // Disable CSRF
        http.csrf().disable();
        
        // Set session management to stateless
        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and();

        // Set unauthorized requests exception handler
        http
            .exceptionHandling()
			.authenticationEntryPoint(customAuthenticationEntryPoint);

        // Set permissions on endpoints
        http.authorizeRequests()
            // Our public endpoints
            .antMatchers("/user-service/**").hasAuthority(AuthorityEnum.USER.name())
            .antMatchers("/login-service/**").permitAll()
            // Our private endpoints
            .anyRequest().authenticated().and().authenticationProvider(customAuthenticationProvider);

        // Add JWT token filter
        http.addFilter(jwtFilter);
		
    }
}

