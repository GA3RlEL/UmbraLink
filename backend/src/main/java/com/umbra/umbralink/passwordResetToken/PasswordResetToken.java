package com.umbra.umbralink.passwordResetToken;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.umbra.umbralink.helper.BaseModel;
import com.umbra.umbralink.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class PasswordResetToken extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime expiresAt;


    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;

}
