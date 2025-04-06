package com.umbra.umbralink.passwordResetToken;

import jakarta.transaction.Transactional;
import jnr.constants.platform.Local;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class PasswordResetCleaner {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public PasswordResetCleaner(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void cleanExpiredTokens(){
        passwordResetTokenRepository.deleteAllExpired();
    }
}
