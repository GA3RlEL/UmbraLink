package com.umbra.umbralink.passwordResetToken;

import com.umbra.umbralink.email.EmailSenderService;
import com.umbra.umbralink.error.NotFoundError;
import com.umbra.umbralink.user.UserEntity;
import com.umbra.umbralink.user.UserRepository;
import jakarta.mail.MessagingException;
import jnr.a64asm.Offset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService{

    @Value("${SMTP_FRONTEND_ENDPOINT}")
    private String frontEndEndpoint;

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailSenderService emailSenderService;

    public PasswordResetTokenServiceImpl(UserRepository userRepository, PasswordResetTokenRepository passwordResetTokenRepository, EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public PasswordResetToken createToken(String email) throws MessagingException {
        Optional<PasswordResetToken> tokenOptional = passwordResetTokenRepository.findByUserEmail(email);

        if(tokenOptional.isEmpty()){
            UserEntity user = userRepository.findByEmail(email).orElseThrow(()-> new NotFoundError("User with following " +
                    "email does not exist"));

            String tokenStr = UUID.randomUUID().toString();
            PasswordResetToken token = new PasswordResetToken();
            token.setExpiresAt(LocalDateTime.now().plusMinutes(60));
            token.setToken(tokenStr);
            token.setUser(user);

            String body = "<p>To reset your password, please click the link below:</p>" +
                    String.format("<p><a href=\"%s\">%s</a></p>", frontEndEndpoint+"/restore-password/"+tokenStr,
                            tokenStr) +
                    "<p>This will redirect you to a page where you can choose a new password for your account.</p>";

            emailSenderService.sendEmail(email,"Umbralink password reset request", body);
            return passwordResetTokenRepository.save(token);
        } else{
            PasswordResetToken token = tokenOptional.get();
            String tokenStr = UUID.randomUUID().toString();
            token.setExpiresAt(LocalDateTime.now().plusMinutes(60));
            token.setToken(tokenStr);

            String body = "<p>To reset your password, please click the link below:</p>" +
                    String.format("<p><a href=\"%s\">%s</a></p>", frontEndEndpoint+"/restore-password/"+tokenStr,
                            tokenStr) +
                    "<p>This will redirect you to a page where you can choose a new password for your account.</p>";

            emailSenderService.sendEmail(email,"Umbralink password reset request", body);

            return passwordResetTokenRepository.save(token);
        }

    }

    @Override
    public Boolean validateToken(String token) {
        PasswordResetToken resetToken =
                passwordResetTokenRepository.findByToken(token).orElseThrow(()-> new NotFoundError("Invalid token"));

        return true;
    }
}
