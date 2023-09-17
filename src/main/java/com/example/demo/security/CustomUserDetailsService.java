package com.example.demo.security;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of Spring Security's UserDetailsService interface
 * for loading user details by username during authentication.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    /**
     * Load user details by username.
     *
     * @param username The username (in this case, the user's email address).
     * @return A UserDetails object representing the user, or throw a UsernameNotFoundException if the user is not found.
     * @throws UsernameNotFoundException If the user with the given username is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = null;
        try {
            user = userService.findByEmail(username)
                    .orElseThrow(() -> new Exception("User Name " + username + " not found"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new CustomUserDetails(user);

    }
}
