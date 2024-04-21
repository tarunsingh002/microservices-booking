package com.microservice.bookingService.externalService;

import com.microservice.bookingService.dto.ValidateTokenResponse;
import com.microservice.bookingService.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "identity-service")
public interface UserService {

    @GetMapping("/api/v1/identity/user/getuser/{id}")
    public User getUser(@PathVariable int id, @RequestHeader("Authorization") String token);

    @GetMapping("/api/v1/identity/all/validatetoken/{token}")
    public ValidateTokenResponse validateToken(@PathVariable String token);
}
