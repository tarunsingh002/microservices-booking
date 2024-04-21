package com.microservice.gatewayService.filter;

import com.microservice.gatewayService.dto.ValidateTokenResponse;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                try {
                    if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                        throw new RuntimeException("missing auth header");
                    }

                    String authHeader = exchange.getRequest().getHeaders().get(org.springframework.http.HttpHeaders.AUTHORIZATION).get(0);

                    if (authHeader != null && authHeader.startsWith("Bearer ")) {
                        String token = authHeader.substring(7);
                        System.out.println(token);
                        return webClientBuilder.build().get()
                                .uri("https://identity-service/api/v1/identity/all/validatetoken/" + token)
                                .retrieve().bodyToMono(ValidateTokenResponse.class)
                                .flatMap(validateTokenResponse -> {
                                    System.out.println(validateTokenResponse);

                                    try {
                                        if (!validateTokenResponse.isTokenIsValid()) {
                                            throw new RuntimeException("Token is not valid");
                                        }
                                        if (exchange.getRequest().getPath().toString().contains("admin") && !validateTokenResponse.isAdmin()) {
                                            throw new RuntimeException("Resource requested requires Admin privileges");
                                        }
                                        if (exchange.getRequest().getPath().toString().contains("user") && validateTokenResponse.isAdmin()) {
                                            throw new RuntimeException("Resource requested requires User privileges");
                                        }
                                    }
                                    catch (Exception e) {
                                        System.out.println(e.getMessage());
                                        ServerHttpResponse response = exchange.getResponse();
                                        response.setStatusCode(HttpStatus.UNAUTHORIZED);
                                        return response.setComplete();
                                    }
                                    return chain.filter(exchange);
                                });
//                        validateTokenResponse = restTemplate.getForObject("http://identity-service/api/v1/identity/validatetoken/" + token, ValidateTokenResponse.class);

                    } else {
                        throw new RuntimeException("Invalid Auth Header");
                    }


                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.setComplete();
                }

            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
