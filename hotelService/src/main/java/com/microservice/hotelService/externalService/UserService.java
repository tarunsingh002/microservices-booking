package com.microservice.hotelService.externalService;

import com.microservice.hotelService.dto.ValidateTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "identity-service")
public interface UserService {

    @GetMapping("/api/v1/identity/all/validatetoken/{token}")
    public ValidateTokenResponse validateToken(@PathVariable String token);
}
