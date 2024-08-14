package com.gc.news_subscription_bot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // Convertimos la clase en un manejador global de excepciones
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class) //se ejecuta cada vez que se una validación falla y se lanza una excepción
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        //Instanciamos un Hashmap por su facilidad clave:valor
        Map<String, String> errors = new HashMap<>();
        // Iteramos la lista de errores y cargamos el Hash con
        // nombre_del_error, mensaje_del_error
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            //Error
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    //Otros métodos para manejar otras excepciones
    //Manejamos suscripciones no encontradas
    @ExceptionHandler(SuscriptionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleSuscriptionNotFoundException(SuscriptionNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    //Manejamos categorías no encontradas
    @ExceptionHandler(InvalidCategoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInvalidCategoryException(InvalidCategoryException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
