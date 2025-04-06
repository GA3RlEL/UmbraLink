package com.umbra.umbralink.passwordResetToken;

import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/token")
public class PasswordResetTokenController {

    private final PasswordResetTokenService passwordResetTokenService;

    public PasswordResetTokenController(PasswordResetTokenService passwordResetTokenService) {
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @PostMapping("/reset/password")
    public PasswordResetToken createResetToken(@RequestBody Map<String,String> data) throws MessagingException {
        String email = data.get("email");
        return passwordResetTokenService.createToken(email);
    }

    @PostMapping("validate")
    public Boolean isTokenValid(@RequestBody Map<String,String> data){
        String token = data.get("token");
        return passwordResetTokenService.validateToken(token);
    }

}
