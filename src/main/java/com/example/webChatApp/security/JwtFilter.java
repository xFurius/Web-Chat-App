package com.example.webChatApp.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.webChatApp.user.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private MyUserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("TOKEN")){
                    String token = cookie.getValue();
                    if(token == null || token.equals("")){
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid token");
                    }else{
                        try{
                            String email = jwtTokenUtil.validateToken(token);
                            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());

                            if(SecurityContextHolder.getContext().getAuthentication() == null){
                                SecurityContextHolder.getContext().setAuthentication(auth);
                            }
                        }catch(JWTVerificationException e){
                            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid token");
                        }
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
