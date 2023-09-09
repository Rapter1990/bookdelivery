package com.example.demo.security;

import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // User user = userService.findByUsername(username)
        //        .orElseThrow(() -> new UserNotFoundException("User Name " + username + " not found"));

        // return new CustomUserDetails(user);

        return null;
    }
}
