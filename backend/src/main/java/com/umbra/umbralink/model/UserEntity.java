package com.umbra.umbralink.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private LocalDateTime lastSeen;

    @OneToMany(mappedBy = "sender")
    @JsonManagedReference
    private List<Conversation> chatsAsSender;
    @OneToMany(mappedBy = "recipient")
    @JsonManagedReference
    private List<Conversation> chatsAsRecipient;


}
