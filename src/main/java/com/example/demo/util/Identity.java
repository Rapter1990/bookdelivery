package com.example.demo.util;

import com.example.demo.model.enums.BeanScope;
import com.example.demo.model.enums.TokenClaims;
import com.example.demo.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@Scope(value = BeanScope.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequiredArgsConstructor
public class Identity {


    private Jwt getJwt() {
        return ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public String getId() {
        return this.getJwt().getClaim(TokenClaims.ID.getValue());
    }

    public String getRole() {
        return this.getJwt().getClaim(TokenClaims.ROLES.getValue());
    }

    public String getUsername() {
        return this.getJwt().getClaim(TokenClaims.USERNAME.getValue());
    }

    public String getEmail() {
        return this.getJwt().getClaim(TokenClaims.EMAIL.getValue());
    }

    public String getFullName() {
        return this.getJwt().getClaim(TokenClaims.USER_FULL_NAME.getValue());
    }

    public CustomUserDetails getCustomerUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return (CustomUserDetails) userDetails;
    }

}
