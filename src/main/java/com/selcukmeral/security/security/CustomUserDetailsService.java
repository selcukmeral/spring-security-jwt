package com.selcukmeral.security.security;

import java.util.Objects;

import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.selcukmeral.security.model.UserModel;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private final SecurityPropertiesConfiguration securityProperties;

    private final ObjectMapper objectMapper;
    
    private final TokenHelper tokenHelper;

	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
		final String jwtToken = (String) token.getPrincipal();
		try {
			final Claims claims = tokenHelper.getClaimsFromToken(jwtToken);

			if (!Objects.equals(claims.getIssuer(), securityProperties.getTokenIssuer())) {
				throw new UsernameNotFoundException("unexpected issuer: " + claims.getIssuer());
			}

			if(!claims.containsKey(securityProperties.getTokenAuthoritiesName())) {
				throw new UsernameNotFoundException("Token authorities name not found in claims");
			}
			final String authoritiesStr = claims.get(securityProperties.getTokenAuthoritiesName(), String.class);
			final UserModel user = objectMapper.readValue(authoritiesStr, UserModel.class);
			return user;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

}
