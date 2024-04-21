package com.microservice.identity.service;

import com.microservice.identity.dto.*;
import com.microservice.identity.entity.Role;
import com.microservice.identity.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

   public JWTAuthResponse signUp(SignUpRequest signUpRequest) {
        if (userService.userExists(signUpRequest.getEmail())) return null;
        else {
            User user = new User();
            user.setEmail(signUpRequest.getEmail());
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            user.setRole(Role.User);
            user = userService.addUser(user);
            return signIn(new SignInRequest(signUpRequest.getEmail(), signUpRequest.getPassword()));
        }
    }

    public JWTAuthResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        if (!userService.userExists(signInRequest.getEmail())) return null;
        else {
            User user = new User();
            user = (User) userService.loadUserByUsername(signInRequest.getEmail());
            int id = user.getUserId();
            boolean isAdmin = userService.isAdmin(user);
            String jwt = jwtService.generateToken(user, 1000 * 60 * 60);
            Long jwtExpiration = System.currentTimeMillis() + 1000 * 60 * 60;
            String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user,
                    1000L * 60 * 60 * 24 * 15);
            Long refreshTokenExpiration = System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 15;
//            JWTAuthRequest jwtAuthRequest = new JWTAuthRequest(jwt, refreshToken);
            JWTAuthResponse jwtAuthResponse = new JWTAuthResponse(id, jwt, refreshToken,
                    jwtExpiration, refreshTokenExpiration, isAdmin);
            return jwtAuthResponse;
        }
    }

    public JWTAuthResponse refreshToken(JWTAuthRequest jwtAuthRequest) {
        String userEmail = jwtService.extractUsername(jwtAuthRequest.getRefreshToken());
        if (userService.userExists(userEmail) && jwtService.isTokenValid(jwtAuthRequest.getRefreshToken(), userService.loadUserByUsername(userEmail))) {
            User user = (User) userService.loadUserByUsername(userEmail);
            String jwt = jwtService.generateToken(userService.loadUserByUsername(userEmail),
                    1000 * 60 * 60);
            Long jwtExpiration = System.currentTimeMillis() + 1000 * 60 * 60;
            Long refreshTokenExpiration = jwtService
                    .extractExpirationDate(jwtAuthRequest.getRefreshToken())
                    .getTime();

            boolean isAdmin = userService.isAdmin(user);

            JWTAuthResponse jwtAuthResponse = new JWTAuthResponse(user.getUserId(), jwt,
                    jwtAuthRequest.getRefreshToken(), jwtExpiration, refreshTokenExpiration,
                    isAdmin);
            return jwtAuthResponse;
        }
        return null;
    }


}
