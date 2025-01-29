package com.umbra.umbralink.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.umbra.umbralink.model.enums.UserStatus;
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


}
