package com.gc.news_subscription_bot.controller;

import com.gc.news_subscription_bot.service.JwtService;
import com.gc.news_subscription_bot.dto.AuthRequest;
import com.gc.news_subscription_bot.dto.AuthResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/login")
public class AuthController {
    @Autowired //Inyectamos instancia
    private JwtService jwtService;

    @PostMapping //Solicitud POST crea token de autenticación
    public ResponseEntity<AuthResponse> createAuthenticationToken(@RequestBody AuthRequest authRequest) {

        // Generar el token JWT
        String token = jwtService.generateToken(authRequest.getUsername());

        // Devolver el token en la respuesta
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new AuthResponse(token));
    }
}
