package com.umbra.umbralink.error;

public class AuthError extends RuntimeException {
    public AuthError(String message) {
        super(message);
    }
}
