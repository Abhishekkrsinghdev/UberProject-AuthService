package com.example.UberAuthService.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService implements CommandLineRunner {
    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secret}")
    private String SECRET;

    private String createToken(Map<String,Object> payload,String email){
        Date now = new Date();
        Date expiryDate=new Date(now.getTime()+expiry*1000L);
        return Jwts.builder()
                .claims(payload)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiryDate)
                .subject(email)
                .signWith(getSignKey())
                .compact();
    }

    private Claims extractAllPayloads(String token){
        return Jwts
                .parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractClaim(String token,Function<Claims,T> claimResolver){
        final Claims claims=extractAllPayloads(token);
        return claimResolver.apply(claims);
    }

    private Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token){
       return extractExpiration(token).before(new Date());
    }

    private String extractEmail(String token){
        return extractClaim(token,Claims::getSubject);
    }

    private Object extractPayload(String token,String payloadKey){
        Claims claim=extractAllPayloads(token);
        return (Object) claim.get(payloadKey);
    }

    private SecretKey getSignKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    private Boolean validateToken(String token,String email){
        final String userEmailFetchedFromToken=extractEmail(token);
        return (userEmailFetchedFromToken.equals(email)) && !isTokenExpired(token);
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String,Object> mp=new HashMap<>();
        mp.put("email","a@b.com");
        mp.put("phoneNumber","99999999");
        String result=createToken(mp,"abhishek");
        System.out.println("Generated token is: " + result);
        System.out.println("phone number is " + extractPayload(result,"phoneNumber").toString());
        System.out.println("email is " + extractPayload(result,"email").toString());
    }
}
