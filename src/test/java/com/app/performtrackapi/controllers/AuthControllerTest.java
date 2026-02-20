package com.app.performtrackapi.controllers;

import com.app.performtrackapi.dtos.Auth.AuthRequest;
import com.app.performtrackapi.dtos.Auth.AuthResponse;
import com.app.performtrackapi.dtos.User.UserResponseDto;
import com.app.performtrackapi.security.JwtToken;
import com.app.performtrackapi.services.User.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtToken jwtToken;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_Success() {
        // Arrange (Preparar)
        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail("test@example.com");
        authRequest.setPassword("password");

        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setEmail("test@example.com");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtToken.generateToken(userDetails)).thenReturn("mocked-jwt-token");
        when(userService.getUserByEmail("test@example.com")).thenReturn(userResponseDto);

        // Act (Actuar)
        ResponseEntity<AuthResponse> response = authController.login(authRequest);

        // Assert (Verificar)
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("mocked-jwt-token", response.getBody().getToken());
        assertEquals("test@example.com", response.getBody().getUser().getEmail());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtToken, times(1)).generateToken(userDetails);
        verify(userService, times(1)).getUserByEmail("test@example.com");
    }
}
