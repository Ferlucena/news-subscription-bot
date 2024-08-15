package com.gc.news_subscription_bot.dto;

/*
* Esta clase AuthRequest será utilizada en el controlador
* para recibir los datos cuando un usuario envíe sus credenciales para autenticarse.
* Esta clase mapear los datos JSON con usrname y pass en un objeto Java para luego procesarlos
* en la aplicación
* */

import lombok.Data;

@Data // Lombok generará automáticamente los getters, setters, toString, equals, y hashCode
public class AuthRequest {
    private String username;
    private String password;
}
