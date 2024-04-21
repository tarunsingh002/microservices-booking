package com.microservice.gatewayService.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/api/v1/identity/all/signup",
            "/api/v1/identity/all/signin",
            "/api/v1/identity/all/refresh");

    public Predicate<ServerHttpRequest> isSecured = request -> {
        if (request.getPath().toString().contains("/api/v1/hotels/all")) {
            System.out.println(request.getPath().toString());
            return false;
        }
        return openApiEndpoints
                .stream()
                .noneMatch(uri->request.getURI().getPath().contains(uri));
    };
}

