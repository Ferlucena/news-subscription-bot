package com.gc.news_subscription_bot.controller;

import com.gc.news_subscription_bot.dto.AuthRequest;

import com.gc.news_subscription_bot.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AuthControllerTest {

    @Mock //Crea un mock "imitacion"
    private JwtService jwtService;

    @InjectMocks //Inyectamos mocks de la instancia probada
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); //Inicializamos los mocks
    }

    @Test //Prueba
    public void testCreateAuthenticationToken() {
        // Arrange: Configuración inicial
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("user");
        authRequest.setPassword("password");

        String token = "dummyToken";
        when(jwtService.generateToken(authRequest.getUsername())).thenReturn(token);

        // Act: Ejecución de la funcionalidad a probar
        ResponseEntity<?> response = authController.createAuthenticationToken(authRequest);

        // Assert: Verificación del resultado esperado
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(token, response.getBody());
    }
}