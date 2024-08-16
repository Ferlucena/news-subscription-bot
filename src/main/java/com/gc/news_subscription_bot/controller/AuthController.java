package com.gc.news_subscription_bot.controller;

import com.gc.news_subscription_bot.service.JwtService;
import com.gc.news_subscription_bot.dto.AuthRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthController {
    @Autowired //Inyectamos instancia
    private JwtService jwtService;

    @PostMapping //Solicitud POST crea token de autenticación
    public ResponseEntity<String> createAuthenticationToken(@RequestBody AuthRequest authRequest) {

        // Generar el token JWT
        String jwt = jwtService.generateToken(authRequest.getUsername());

        // Devolver el token en la respuesta
        return ResponseEntity.ok(jwt);
    }
}
