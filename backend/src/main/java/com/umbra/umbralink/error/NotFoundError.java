package com.umbra.umbralink.error;

public class NotFoundError extends RuntimeException{
    public NotFoundError(String message){
        super(message);
    }
}
