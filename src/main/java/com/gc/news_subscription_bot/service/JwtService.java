package com.gc.news_subscription_bot.service;
/*
* Esta clase es la responsable de generar y validar tokens JWT
* Contiene todos los métodos para cear el token... son muchos
* */

//Notacion para traer la clave secreta
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;//trae la funcion hash


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service //Esta clase es un servicio
public class JwtService {

    // Inyectamos el valor de jwt.secret desde application.properties
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    // Extraer el nombre de usuario del token JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extraer cualquier claim del token JWT
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extraer todos los claims (reclamaciones) del token JWT
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    // Verificar si el token ha expirado
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extraer la fecha de expiración del token JWT
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generar un token JWT para un usuario
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    // Crear el token JWT con los claims y el usuario
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token válido por 10 horas
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Firmar el token con HS256 y la clave secreta
                .compact();
    }

    // Validar el token JWT con el usuario
    public Boolean validateToken(String token, String username) {
        final String usernameFromToken = extractUsername(token);
        return (usernameFromToken.equals(username) && !isTokenExpired(token));
    }
}
