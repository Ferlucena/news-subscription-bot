package com.gc.news_subscription_bot.model;

/*
* Esta clase tiene por objetivo representar las tablas de la BBDD
* es el dominio
* */

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ElementCollection;
import lombok.Data;

import java.util.List;

@Entity //Marcamos la clase como una una entidad JPA --> Se mapea a tabla
@Data   // Generamos getters, setters, toString con anotación
public class Subscription {

    @Id //Especificamos la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id auto
    // Campos de la clase
    private Long id;
    private String phoneNumber;

    // "Cada usuario deberá almacenar una lista de categoría de noticias"
    @ElementCollection // asociamos y almacenamos una lista de valores, será una tupla en la base de datos
    private List<String> categories;

}
