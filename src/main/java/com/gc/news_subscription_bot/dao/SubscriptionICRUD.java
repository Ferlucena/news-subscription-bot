package com.gc.news_subscription_bot.dao;

import com.gc.news_subscription_bot.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//Extendemos SubscriptionDAO de JpaRepository --> heredamos los métodos CRUD

public interface SubscriptionICRUD extends JpaRepository<Subscription, Long>{

    //Devuelve las suscripciones de un usuario basado en su número de
    //teléfono.
    Subscription findByPhoneNumber(String phoneNumer);

    //Optional<Subscription> findByPhoneNumber(String phoneNumber);
}
