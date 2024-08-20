package com.gc.news_subscription_bot.service;

import com.gc.news_subscription_bot.dao.SubscriptionICRUD;
import com.gc.news_subscription_bot.exception.InvalidCategoryException;
import com.gc.news_subscription_bot.exception.SuscriptionNotFoundException;

import com.gc.news_subscription_bot.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
* La capa service se encarga de aplicar las lógicas del negocio
* Cualquier solicitud o ejecución pasa por esta capa
* */

@Service //Marcamos la clase como servicio, contiene logica de negocio
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
/*  ANTES--> PROBLEMAS CON OPTIONAL-->  public Optional<Subscription> getSubscriptionByPhoneNumber(String phoneNumber) {
        return Optional.ofNullable(subscriptionICRUD.findByPhoneNumber(phoneNumber));
    }*/

    //cambio
    public Subscription getSubscriptionByPhoneNumber(String phoneNumber) {
        // Instancia de la excepcion de numero no encontrado
        SuscriptionNotFoundException ex = new SuscriptionNotFoundException("No se encontró la suscripción con el número de teléfono: " + phoneNumber);
        //En caso de encontrarlo
        return subscriptionICRUD.findByPhoneNumber(phoneNumber).orElseThrow(() -> ex);
    }

    // Método de actualización de servicio, incluye el tratamiento de la excepcion en la validación
    public Subscription updateSubscriptionCategories(Long id, List<String> newCategories) {
        Subscription subscription = subscriptionICRUD.findById(id)
                .orElseThrow(() ->  new SuscriptionNotFoundException("Suscripción no encontrada con ID: " + id));

        if (!newCategories.isEmpty() && newCategories.stream().allMatch(Subscription.VALID_CATEGORIES::contains)) {
            subscription.setCategories(newCategories);
            return subscriptionICRUD.save(subscription);
        } else {
            throw new InvalidCategoryException("Algunas categorías proporcionadas no son válidas.");
        }
    }
}
