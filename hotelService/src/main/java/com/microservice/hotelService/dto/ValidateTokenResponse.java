package com.microservice.hotelService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateTokenResponse {
    boolean tokenIsValid;
    boolean isAdmin;
    String email;

}
