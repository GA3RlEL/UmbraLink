package com.umbra.umbralink.passwordResetToken;

import jakarta.mail.MessagingException;

public interface PasswordResetTokenService {
    PasswordResetToken createToken(String email) throws MessagingException;
    Boolean validateToken(String token);
}
