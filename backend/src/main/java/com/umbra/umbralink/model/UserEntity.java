package com.umbra.umbralink.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String email;
  private String password;
  @ElementCollection
  private List<Role> roles = new ArrayList<>(List.of(new Role("USER")));

  @OneToMany(mappedBy = "sender")
  private List<Message> sentMessages;
  @OneToMany(mappedBy = "receiver")
  private List<Message> receivedMessages;
}
