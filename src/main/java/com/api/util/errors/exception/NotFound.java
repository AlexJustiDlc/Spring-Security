package com.api.util.errors.exception;

public class NotFound extends RuntimeException{
    public NotFound(String mensaje){
        super(mensaje);
    }
}
