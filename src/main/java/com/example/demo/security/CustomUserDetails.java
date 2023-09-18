package com.example.demo.security;

import com.example.demo.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Custom implementation of Spring Security's UserDetails interface for handling user authentication and authorization.
 */
@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    @Serial
    private static final long serialVersionUID = 4514751530271704280L;

    private final User user;

    /**
     * Returns the authorities (roles) granted to the user.
     *
     * @return A collection of GrantedAuthority objects, with a single authority based on the user's role.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));
    }

    /**
     * Returns the user's password.
     *
     * @return The user's encoded password.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the user's username.
     *
     * @return The user's username.
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Indicates whether the user's account has not expired.
     *
     * @return `true` if the user's account is not expired, `false` otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is not locked.
     *
     * @return `true` if the user's account is not locked, `false` otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) are not expired.
     *
     * @return `true` if the user's credentials are not expired, `false` otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled (active).
     *
     * @return `true` if the user is enabled, `false` otherwise.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Returns the user's email address.
     *
     * @return The user's email address.
     */
    public String getEmail() {
        return user.getEmail();
    }

    /**
     * Returns the user's ID.
     *
     * @return The user's unique ID.
     */
    public Long getId() {
        return user.getId();
    }

    /**
     * Returns additional claims associated with the user.
     *
     * @return A map of additional claims.
     */
    public Map<String, Object> getClaims() {
        return user.getClaims();
    }

}
