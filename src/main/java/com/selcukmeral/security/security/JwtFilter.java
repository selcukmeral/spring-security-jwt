package com.selcukmeral.security.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends AbstractPreAuthenticatedProcessingFilter {

	
	private final SecurityPropertiesConfiguration securityProperties;;

	@Override
	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		return request.getHeader(securityProperties.getHeaderName());
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return "n/a";
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		String jwt = httpServletRequest.getHeader(securityProperties.getHeaderName());
		if(StringUtils.hasLength(jwt)) {
			//Burada jwttoken değerine ulaşabiliriz
			//token validasyon veya yenileme için kullanılabilir
		}
		super.doFilter(httpServletRequest, httpServletResponse, chain);

	}
}
