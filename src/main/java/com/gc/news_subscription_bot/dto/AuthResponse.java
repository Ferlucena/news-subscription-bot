package com.gc.news_subscription_bot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;  // Importa si utilizas Jackson para trabajar con JSON
import com.fasterxml.jackson.annotation.JsonProperty;  // Importa para especificar nombres de propiedades JSON

@JsonInclude(JsonInclude.Include.NON_NULL)  // Asegura que no se incluyan valores nulos en la serialización JSON
public class AuthResponse {

    @JsonProperty("token")  // Especifica el nombre de la propiedad cuando se serializa y deserializa
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}