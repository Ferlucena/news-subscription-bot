package com.gc.news_subscription_bot.controller;


import com.gc.news_subscription_bot.service.SubscriptionService;
import com.gc.news_subscription_bot.model.Subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Indicamos que la clase es un controlador REST, maneja HTTP y devuelve JSON
@RequestMapping("/subscriptions") //Ruta, endpoint
public class SuscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping //Solicitudes del tipo POST --> Crea nuevas suscripciones
    // ResponseEntity es una clase Spring que maneja resp HTTP cuerpo+encabezado+estado
    // Contiene a nuestro objeto Subscription
    public ResponseEntity<Subscription> createSubscription(@RequestBody Subscription subscription) {
        // @RequestBody, es una especie de desestructuracion del objeto
        // deserializamos la solicitud extrayendo del cuerpo de la solicitud a subscription
        // este proceso se da en formato JSON que se transformará automaticamente en Objeto Suscription
        Subscription createdSubscription = subscriptionService.createSubscription(subscription);
        // Llamamos al método createSubscription de la capa service y pasamos el objeto subscription recibido
        // El método crea una nueva suscripción y devuelve el objeto Suscription

        return new ResponseEntity<>(createdSubscription, HttpStatus.CREATED);
        //un método post en si no devuelve nada comunmente pero con springboot podemos devolver el objeto construido
        // como aviso de resultado ok y un estado al cliente mediante ResponseEntity
    }

    @GetMapping("/{phoneNumber}")// Solicitudes GET en /subscriptions/{phoneNumber}
    public ResponseEntity<Subscription> getSubscription(@PathVariable String phoneNumber) {
        // El parámetro @PathVariable String phoneNumber indica que el valor de {phoneNumber}
        // en la URL se asignará a la variable phoneNumber
        return subscriptionService.getSubscriptionByPhoneNumber(phoneNumber)
                .map(subscription -> new ResponseEntity<>(subscription, HttpStatus.OK))
                //.map es parte de la clase Optional
                // Si el Optional contiene un valor (es decir, la suscripción se encuentra),
                // el método .map aplica la función proporcionada
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
