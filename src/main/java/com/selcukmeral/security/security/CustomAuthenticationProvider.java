package com.selcukmeral.security.security;

import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider extends PreAuthenticatedAuthenticationProvider {

    public CustomAuthenticationProvider(CustomUserDetailsService userDetailsService) {
        setPreAuthenticatedUserDetailsService(userDetailsService);
    }

}
