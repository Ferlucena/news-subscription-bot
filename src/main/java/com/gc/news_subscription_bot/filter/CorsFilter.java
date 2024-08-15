package com.gc.news_subscription_bot.filter;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/*
* CorsFilter permite la conexión del front desde puertos distintos
* */

@Component
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Añadimos los encabezados CORS a la respuesta para permitir cualquier origen
        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers", "*");

        // Opcional: Permitir credenciales en las solicitudes CORS
        // httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

        // Si es una solicitud OPTIONS, responde con el código de estado 200
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        //Continuamos con el siguiente filtro de la cadena
        chain.doFilter(request, response);
    }

}