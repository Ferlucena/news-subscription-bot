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
//import org.hibernate.annotations.processing.Pattern;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity //Marcamos la clase como una una entidad JPA --> Se mapea a tabla
@Data   // Generamos getters, setters, toString con anotación
public class Subscription {

    @Id //Especificamos la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id auto
    // Campos de la clase
    private Long id;

    //regex digitos hasta 9 caracteres
    // en caso de numeros internacionales
    // (regexp="^\\+(?:[0-9] ?){6,14}[0-9]$", message="El número de teléfono debe ser un número internacional válido")
    @Pattern(regexp="^\\+(?:[0-9] ?){6,14}[0-9]$", message="El número de teléfono debe ser un número internacional válido")
    private String phoneNumber;

    // "Cada usuario deberá almacenar una lista de categoría de noticias"
    @NotEmpty(message="Debe seleccionar al menos una categoría") // que no esté vacía
    @Size(min = 1, message = "Debe haber al menos una categoría") // que tenga al menos una categoría
    @ElementCollection // asociamos y almacenamos una lista de valores en la base de datos
    private List<String> categories;

}
