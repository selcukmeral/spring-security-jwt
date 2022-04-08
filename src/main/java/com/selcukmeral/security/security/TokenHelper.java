package com.selcukmeral.security.security;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.selcukmeral.security.model.UserModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenHelper {

	private final SecurityPropertiesConfiguration securityProperties;
	
	private final ObjectMapper objectMapper;


	public String generateToken(UserModel payloadClaims) {

		return this.generateToken(payloadClaims, null);

	}
	
	public String generateToken(UserModel payloadClaims,Date expiryDate) {
		try {
			
			if(expiryDate == null) {
				expiryDate = generateTokenExpirationDate();
			}
			
			return Jwts.builder()
					.claim(securityProperties.getTokenAuthoritiesName(), objectMapper.writeValueAsString(payloadClaims))
					.setIssuer(securityProperties.getTokenIssuer())
					.setIssuedAt(new Date())
					.setExpiration(expiryDate)
					.signWith(SignatureAlgorithm.HS256, securityProperties.getTokenKey()).compact();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	
	public Date generateTokenExpirationDate() {
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.SECOND, securityProperties.getTokenValidity());
		return c.getTime();
	}
	 
	public Claims getClaimsFromToken(String token) {
		 Claims claims = Jwts.parser().setSigningKey(securityProperties.getTokenKey())
	        		.parseClaimsJws(token).getBody();
		return claims;
	}

	public String getTokenFromHeader(HttpServletRequest request) {

		final String jwtToken = request.getHeader(securityProperties.getHeaderName());

		return jwtToken;
	}
	
	public String refreshToken(String jwtToken) {
		try {
			Claims claims = getClaimsFromToken(jwtToken);

			 if(!Objects.equals(claims.getIssuer(), securityProperties.getTokenIssuer())) {
	             throw new RuntimeException("unexpected issuer: " + claims.getIssuer());
	         }
			 
			if (claims.containsKey(securityProperties.getTokenAuthoritiesName())) {
				final String authoritiesStr = claims.get(securityProperties.getTokenAuthoritiesName(), String.class);
				UserModel user = objectMapper.readValue(authoritiesStr, UserModel.class);
				return generateToken(user);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return jwtToken;

	}


}