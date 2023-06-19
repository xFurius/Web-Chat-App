package com.example.webChatApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class Config {
    //for tests only 
    //delete later
    // @Bean
    // public InMemoryUserDetailsManager userDetailsManager(){
    //     UserDetails user = User.builder().username("test").password("{noop}test").build();

    //     return new InMemoryUserDetailsManager(user);
    // }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(config -> config.requestMatchers("/").permitAll()
        .requestMatchers("/register").permitAll()
        .requestMatchers("/app/**").authenticated())
        .formLogin(form -> form.loginPage("/signIn").loginProcessingUrl("/auth").permitAll())
        .logout(logout -> logout.invalidateHttpSession(true).logoutUrl("/signOut").logoutSuccessUrl("/"));
        return http.build();
    }
}
