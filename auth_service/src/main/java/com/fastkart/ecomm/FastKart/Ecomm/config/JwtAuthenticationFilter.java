package com.fastkart.ecomm.FastKart.Ecomm.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Inside JwtAuthenticationFilter");
        log.info("Inside doFilterInternal()");
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        final String roleType;

        if(authHeader == null || !authHeader.startsWith("Bearer" )){
            log.info("Authentication header is null. Hence returned from doFilterInternal()");
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        log.info("Going to fetch username");
        userEmail = jwtService.extractUsername(jwt);

        log.info("Going to fetch roleType");
        roleType = jwtService.extractClaim("role", jwt);


        if(userEmail != null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if(jwtService.isTokenValid(jwt, userDetails)){
                log.info("Token is valid. Adding email and role in the header.");
                request.setAttribute("email", userEmail);
                request.setAttribute("role", roleType);
            }
        }

        filterChain.doFilter(request, response);
        log.info("doFilterInternal() ended.");
    }
}
