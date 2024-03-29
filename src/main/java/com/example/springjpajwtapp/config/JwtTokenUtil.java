package com.example.springjpajwtapp.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    // const session time for jwt token
    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;



    // generate token for user

    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    // help method for token generation
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date((System.currentTimeMillis())))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512,secret).compact();
    }


    // get all info from token with secret key

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    // generic method for gething  any separate claim from token
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    // geting expiration date of token from claims using helper generic mehtod
    public Date getExpirationDate(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // geting username of token from claims using helper generic mehtod
    public String getUserNameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    // chek is token expired
    public Boolean isTokenExpired(String token){

        final Date expiration = getExpirationDate(token);
        return expiration.before(new Date());


    }

    // token validation
    public Boolean validateToken(String token, UserDetails userDetails){
        final String userName = getUserNameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }





}
