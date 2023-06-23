package com.example.webChatApp.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtTokenUtil {
    @Value("${jwt.token.secret}")
    private String tokenSecret;

    public String generateToken(String email){ //add claims later
        String token = JWT.create()
        .withSubject("User Details")
        .withClaim("email", email)
        .withIssuedAt(new Date())
        .withIssuer("IssuerHere")
        .sign(Algorithm.HMAC256(tokenSecret));

        return token;
    }

    public String validateToken(String token) throws JWTVerificationException{
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(tokenSecret))
        .withSubject("User Details")
        .withIssuer("IssuerHere")
        .build();

        DecodedJWT decodedToken = verifier.verify(token);
        return decodedToken.getClaim("email").asString();
    }
}
