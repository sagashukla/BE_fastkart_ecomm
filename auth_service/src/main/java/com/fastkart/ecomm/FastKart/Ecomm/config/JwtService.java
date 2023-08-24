package com.fastkart.ecomm.FastKart.Ecomm.config;

import com.fastkart.ecomm.FastKart.Ecomm.dto.GenericErrorResponse;
import com.fastkart.ecomm.FastKart.Ecomm.exception.InvalidJwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.service.signin.key}")
    private String SECERET_KEY;

    public String extractUsername(String token) {
        log.info("Inside JwtService");
        log.info("Inside extractUsername()");
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails){
        log.info("Inside JwtService");
        log.info("Inside generateToken()");
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, String> extraClaims, UserDetails userDetails){
        log.info("Inside JwtService");
        log.info("Inside generateToken()");
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // Valid for 1 day + 1000 miliseconds
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        log.info("Inside JwtService");
        log.info("Inside isTokenValid()");
        final String username = extractUsername(token);

        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        log.info("Inside JwtService");
        log.info("Inside isTokenExpired()");
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        log.info("Inside JwtService");
        log.info("Inside extractExpiration()");
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        log.info("Inside JwtService");
        log.info("Inside extractClaim()");
        final Claims claims = extratAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extratAllClaims(String token){
        log.info("Inside JwtService");
        log.info("Inside extratAllClaims()");
        try{
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (Exception err){
            throw new InvalidJwtTokenException("Invalid token provided");
        }
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractClaim(String key, String token){
        log.info("Inside JwtService");
        log.info("Inside extractClaim()");
        Claims claims = extratAllClaims(token);
        return claims.get(key, String.class);
    }

    private Key getSignInKey() {
        log.info("Inside JwtService");
        log.info("Inside getSignInKey()");
        byte[] keyBytes = Decoders.BASE64.decode(SECERET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
