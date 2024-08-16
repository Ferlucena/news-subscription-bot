package com.gc.news_subscription_bot.filters;


import jakarta.servlet.*;

import java.io.IOException;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.gc.news_subscription_bot.service.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.ArrayList;

@Component
public class JwtRequestFilter implements Filter {

    // Inyección del servicio que maneja la lógica relacionada con JWT.
    @Autowired
    private JwtService jwtService;

    @Override
    // Método doFilter es el punto de entrada para cada solicitud que pasa por este filtro.
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Convertimos el ServletRequest a HttpServletRequest para acceder a los métodos específicos de HTTP.
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // Obtener el valor del encabezado 'Authorization' que debe contener el token JWT.
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        // Verificamos si el encabezado de autorización no es nulo y si comienza con "Bearer ".
        // Esto indica que el encabezado contiene un token JWT.
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extraemos el token JWT eliminando el prefijo "Bearer ".
            String jwt = authorizationHeader.substring(7);
            // Extraemos el nombre de usuario del token JWT.
            String username = jwtService.extractUsername(jwt);

            // Validamos el token y el nombre de usuario. Ambos deben ser válidos para proceder.
            if (jwtService.validateToken(jwt,username)) {
                // Si el token es válido, configuramos la autenticación en el contexto de seguridad.
                // Esto efectivamente "inicia sesión" al usuario para esta solicitud.
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // Continuar con la cadena de filtros, lo que significa que la solicitud procede al siguiente filtro o al destino final.
        chain.doFilter(request, response);
    }
}
