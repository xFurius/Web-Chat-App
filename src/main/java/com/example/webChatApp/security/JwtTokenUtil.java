package com.example.webChatApp.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.webChatApp.user.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class JwtTokenUtil {
    @Value("${jwt.token.secret}")
    private String tokenSecret;
    private UserRepository repository;

    @Autowired
    public JwtTokenUtil(UserRepository repository){
        this.repository = repository;
    }

    public String generateToken(String email){
        String token = JWT.create()
        .withClaim("email", email)
        .withClaim("UID", repository.findByEmail(email).get().getUID())
        .withIssuedAt(new Date())
        .withIssuer("webChatApp")
        .sign(Algorithm.HMAC256(tokenSecret));

        return token;
    }

    public String validateToken(String token) throws JWTVerificationException{
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(tokenSecret))
        .withIssuer("webChatApp")
        .build();

        DecodedJWT decodedToken = verifier.verify(token);
        return decodedToken.getClaim("email").asString();
    }

    public String extractUID(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(tokenSecret))
        .withIssuer("webChatApp")
        .build();

        DecodedJWT decodedToken = verifier.verify(token);
        return decodedToken.getClaim("UID").asString();
    }

    public String getToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("TOKEN")){
                    String token = cookie.getValue();
                    return token;
                }
            }
        }
        return null;
    }
}
