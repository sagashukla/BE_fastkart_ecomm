package com.fastkart.ecomm.FastKart.Ecomm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}
