package com.example.webChatApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import com.example.webChatApp.user.MyUserDetailsService;
import com.example.webChatApp.user.UserRepository;

import lombok.AllArgsConstructor;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class Config {
    private UserRepository userRepository;
    private JwtFilter jwtFilter;
    private MyUserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(crsf -> crsf.disable())
        .httpBasic(basic -> basic.disable())
        .authorizeHttpRequests(config -> config
        .requestMatchers("/register", "/home", "/auth", "/logout").permitAll()
        .requestMatchers("/js/**", "/css/**").permitAll()
        .requestMatchers("/chat", "/app", "/websocket", "/chat/**", "/app/**").permitAll()
        .requestMatchers("/messages","/messages/**").authenticated()
        .requestMatchers("/user/**").authenticated())
        .userDetailsService(userDetailsService)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .formLogin(login -> login.loginPage("/signIn").permitAll())
        .logout(logout -> logout.clearAuthentication(true).deleteCookies("TOKEN").invalidateHttpSession(true).logoutSuccessUrl("/home"));
        
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
