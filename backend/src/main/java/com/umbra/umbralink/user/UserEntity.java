package com.umbra.umbralink.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.umbra.umbralink.helper.BaseModel;
import com.umbra.umbralink.image.Image;
import com.umbra.umbralink.message.Message;
import com.umbra.umbralink.enums.UserStatus;
import com.umbra.umbralink.passwordResetToken.PasswordResetToken;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String role = "USER";
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.OFFLINE;

    @OneToMany(mappedBy = "sender")
    @JsonManagedReference
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "receiver")
    @JsonManagedReference
    private List<Message> receivedMessages;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Image profileImage;

    @OneToMany(mappedBy = "photoMessageUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Image> photoMessageUser;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL ,orphanRemoval = true)
    @JsonManagedReference
    private PasswordResetToken resetToken;

}
