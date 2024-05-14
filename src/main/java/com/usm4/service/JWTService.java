package com.usm4.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.usm4.entity.AppUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class JWTService {

    @Value("${jwt.algorithmKey}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiryDuration}")
    private int expiryTime;

    private Algorithm algorithm;

    private final static String USER_NAME = "name";

    @PostConstruct
    public void postConstruct(){ //when project start this method automatically run

        algorithm = Algorithm.HMAC256(algorithmKey); // here encoded algorithmKey
    }


    public String generateToken(AppUser appUser){
        return JWT.create()
                .withClaim(USER_NAME,appUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+expiryTime))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public String getUserName(String token){
        DecodedJWT decodedJwt = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return decodedJwt.getClaim(USER_NAME).asString();
    }




}
