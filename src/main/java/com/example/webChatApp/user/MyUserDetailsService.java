package com.example.webChatApp.user;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<com.example.webChatApp.user.User> userDetails = repository.findByEmail(email);
        if(userDetails.isEmpty()){
            throw new UsernameNotFoundException("user with email "+email+" not found");
        }
        com.example.webChatApp.user.User user = userDetails.get();

        return new org.springframework.security.core.userdetails.User(email, user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    
}
