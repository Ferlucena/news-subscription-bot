package com.gc.news_subscription_bot.config;

import jakarta.servlet.Filter; //NO FUNCIONA
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gc.news_subscription_bot.filters.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /*@Bean // DOS BEANS ROMPEN EL CÓDIGO
    public Filter jwtRequestFilter() {
        return (Filter) new JwtRequestFilter();
    }*/

    @Autowired
    private JwtRequestFilter jwtRequestFilter;  // Inyectamos el filtro

    //Filtro para token
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Deshabilitamos CSRF, útil para servicios REST donde el cliente no maneja cookies
                // reglas de autorización para las solicitudes HTTP
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/login").permitAll()  // `/login` no requiere autenticación
                        .requestMatchers("/subscriptions/*").permitAll()  // Permite acceso sin autenticación a `/subscriptions`
                        .requestMatchers("/subscriptions").authenticated()  // Requiere autenticación para /subscriptions
                        .anyRequest().authenticated()  // Cualquier otra solicitud requiere autenticación
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);  // Añade el filtro JWT antes del proceso de autenticación

        return http.build();
    }
}
