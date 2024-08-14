package com.gc.news_subscription_bot.service;

import com.gc.news_subscription_bot.dao.SubscriptionICRUD;
import com.gc.news_subscription_bot.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
* La capa service se encarga de aplicar las lógicas del negocio
* Cualquier solicitud o ejecución pasa por esta capa
* */

@Service
public class SubscriptionService {
    /*Esto es declarar el objeto primero el objeto e instanciarlo con la implementacion
     * principio de Liskov*/

    @Autowired //inyectamos una instancia de subscriptionICRUD
    private SubscriptionICRUD subscriptionICRUD;


    //Crear una subscripción
    public Subscription createSubscription(Subscription subscription) {
        return subscriptionICRUD.save(subscription);
    }

    // Obtener una suscripción
    // Trabajamos con Optional para manejar valores nulos.
    // Verifica si el valor está presente antes de intentar usarlo, evitando NullPointerException.
    public Optional<Subscription> getSubscriptionByPhoneNumber(String phoneNumber) {
        return Optional.ofNullable(subscriptionICRUD.findByPhoneNumber(phoneNumber));
    }
}
