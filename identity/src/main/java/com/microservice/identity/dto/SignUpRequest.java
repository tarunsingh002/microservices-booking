package com.microservice.identity.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String email;
    private String password;
}
