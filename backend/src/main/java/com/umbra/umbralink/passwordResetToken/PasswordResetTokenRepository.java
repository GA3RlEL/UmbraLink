package com.umbra.umbralink.passwordResetToken;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM password_reset_token t WHERE t.expires_at < NOW() AT TIME ZONE 'UTC'", nativeQuery = true)
    void deleteAllExpired();

    Optional<PasswordResetToken> findByToken(String token);

    Optional<PasswordResetToken> findByUserEmail(String email);

}
