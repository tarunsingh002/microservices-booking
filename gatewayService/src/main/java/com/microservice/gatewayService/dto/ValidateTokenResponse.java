package com.microservice.gatewayService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateTokenResponse {
    boolean tokenIsValid;
    boolean isAdmin;
    String email;

}
