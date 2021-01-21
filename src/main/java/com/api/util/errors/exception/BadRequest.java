package com.api.util.errors.exception;

public class BadRequest extends RuntimeException{
    public BadRequest(String mensaje){
        super(mensaje);
    }
}
