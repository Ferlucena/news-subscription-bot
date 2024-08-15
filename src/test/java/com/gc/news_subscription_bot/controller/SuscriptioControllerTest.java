package com.gc.news_subscription_bot.controller;

import com.gc.news_subscription_bot.exception.SuscriptionNotFoundException;
import com.gc.news_subscription_bot.model.Subscription;
import com.gc.news_subscription_bot.dao.SubscriptionICRUD;
import com.gc.news_subscription_bot.service.SubscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubscriptionServiceTest {

    @Mock
    private SubscriptionICRUD subscriptionICRUD;

    @InjectMocks
    private SubscriptionService subscriptionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSubscription_Success() {
        // Datos de prueba
        String phoneNumber = "1234567890";
        Subscription expectedSubscription = new Subscription();
        expectedSubscription.setPhoneNumber(phoneNumber);

        // Simular comportamiento del repositorio
        when(subscriptionICRUD.findByPhoneNumber(phoneNumber))
                .thenReturn(Optional.of(expectedSubscription));

        // Ejecutar el método y verificar el resultado
        Subscription actualSubscription = subscriptionService.getSubscriptionByPhoneNumber(phoneNumber);
        assertEquals(expectedSubscription, actualSubscription, "La suscripción obtenida no coincide con la esperada.");
    }

    @Test
    void testGetSubscription_NotFound() {
        /// Datos de prueba
        String phoneNumber = "1234567891";

        // Simular comportamiento del repositorio
        when(subscriptionICRUD.findByPhoneNumber(phoneNumber))
                .thenReturn(Optional.empty());

        // Verificar que se lance la excepción esperada
        SuscriptionNotFoundException thrown = assertThrows(SuscriptionNotFoundException.class,
                () -> subscriptionService.getSubscriptionByPhoneNumber(phoneNumber),
                "Se esperaba que se lanzara SuscriptionNotFoundException");

        assertEquals("No se encontró la suscripción con el número de teléfono: " + phoneNumber, thrown.getMessage());
    }
}
