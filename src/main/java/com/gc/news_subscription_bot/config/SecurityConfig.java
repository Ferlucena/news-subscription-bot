package com.gc.news_subscription_bot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //Filtro para token
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/subscriptions", "/subscriptions/*").permitAll()  // Permitir sin autenticación
                        .anyRequest().authenticated()  // Cualquier otra solicitud requiere autenticación
                )
                .csrf(csrf -> csrf.disable());   // Deshabilitar CSRF para APIs, si aplicable

        return http.build();
    }
}
