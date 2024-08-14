package com.gc.news_subscription_bot.exception;

public class SuscriptionNotFoundException extends RuntimeException {
    //Método para manejar una suscripcion no encontrada
    public SuscriptionNotFoundException(String mssge) {
        super(mssge);
    }
}
