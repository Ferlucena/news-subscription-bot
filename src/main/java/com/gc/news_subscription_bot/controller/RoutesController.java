package com.gc.news_subscription_bot.controller;


import com.gc.news_subscription_bot.dao.SubscriptionICRUD;
import com.gc.news_subscription_bot.service.SubscriptionService;
import com.gc.news_subscription_bot.model.Subscription;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController // Indicamos que la clase es un controlador REST, maneja HTTP y devuelve JSON
@RequestMapping("/subscriptions") //Ruta, endpoint
public class RoutesController {
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private SubscriptionICRUD subscriptionICRUD;

    @PostMapping //Solicitudes del tipo POST --> Crea nuevas suscripciones
    // ResponseEntity es una clase Spring que maneja resp HTTP cuerpo+encabezado+estado
    // Contiene a nuestro objeto Subscription
    public ResponseEntity<Object> createSubscription(@Valid @RequestBody Subscription subscription, BindingResult result) {
        // @Valid el objeto suscripcion debe ser validado al principio
        // BindingResult (resultado vinculante) si hay errores se almacenan aqui
        // @RequestBody, es una especie de desestructuracion del objeto
        // deserializamos la solicitud extrayendo del cuerpo de la solicitud a subscription
        // este proceso se da en formato JSON que se transformará automaticamente en Objeto Suscription


        //En caso de recibir una suscripcion con errores
        //Listamos los errores para poder devolver un objeto
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        //Autenticacion
        // Obtener el nombre de usuario del contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null) ? authentication.getName() : null;

        // Llamamos al método createSubscription de la capa service y pasamos el objeto subscription recibido
        // El método crea una nueva suscripción y devuelve el objeto Suscription
        Subscription createdSubscription = subscriptionService.createSubscription(subscription);


        // Asociar el nombre de usuario con la suscripción si es necesario
        if (username != null) {
            subscription.setPhoneNumber(username);
        }

        return new ResponseEntity<>(createdSubscription, HttpStatus.CREATED);
        // un método post en si no devuelve nada comunmente pero con springboot podemos devolver el objeto construido
        // como aviso de resultado ok y un estado al cliente mediante ResponseEntity
    }

    @GetMapping("/{phoneNumber}")// Solicitudes GET en /subscriptions/{phoneNumber}
    public ResponseEntity<Subscription> getSubscription(@PathVariable String phoneNumber) {
        // El parámetro @PathVariable String phoneNumber indica que el valor de {phoneNumber}
        // en la URL se asignará a la variable phoneNumber

        //Instanciamos la suscripción
        Subscription subscription = subscriptionService.getSubscriptionByPhoneNumber(phoneNumber);

        if (subscription != null) {
            return new ResponseEntity<>(subscription, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/{phoneNumber}")
    public ResponseEntity<Subscription> updateSubscriptionByPhone(@PathVariable String phoneNumber, @RequestBody Subscription subscriptionDetails) {
        Subscription subscription = subscriptionService.getSubscriptionByPhoneNumber(phoneNumber);

        // Actualizar los campos de la suscripción
        subscription.setPhoneNumber(subscriptionDetails.getPhoneNumber());  // Considerar la necesidad de este campo
        subscription.setCategories(subscriptionDetails.getCategories());

        Subscription updatedSubscription = subscriptionICRUD.save(subscription);
        return ResponseEntity.ok(updatedSubscription);
    }
}
