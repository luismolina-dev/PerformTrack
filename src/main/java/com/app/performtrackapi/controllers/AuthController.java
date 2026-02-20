package com.app.performtrackapi.controllers;

import com.app.performtrackapi.dtos.Auth.AuthRequest;
import com.app.performtrackapi.dtos.Auth.AuthResponse;
import com.app.performtrackapi.dtos.User.UserResponseDto;
import com.app.performtrackapi.security.JwtToken;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.performtrackapi.services.User.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtToken jwtToken;

    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtToken jwtToken, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtToken = jwtToken;
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        assert userDetails != null;
        String jwt = jwtToken.generateToken(userDetails);
        UserResponseDto userResponseDto = userService.getUserByEmail(authRequest.getEmail());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwt);
        authResponse.setUser(userResponseDto);

        return ResponseEntity.ok(authResponse);
    }
}
