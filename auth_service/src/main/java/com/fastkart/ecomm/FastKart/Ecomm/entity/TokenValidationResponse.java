package com.fastkart.ecomm.FastKart.Ecomm.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;


@Getter
@Builder
@ToString
@Data
public class TokenValidationResponse {
    private String status;
    private boolean isAuthenticated;
    private String methodType;
    private String email;
    private String roleType;
}
