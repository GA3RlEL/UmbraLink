package com.umbra.umbralink.error;

public class UnauthorizedConversationAccessException extends RuntimeException{
    public UnauthorizedConversationAccessException(String message){
        super(message);
    }
}
