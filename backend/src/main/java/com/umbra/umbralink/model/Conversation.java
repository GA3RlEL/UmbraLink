package com.umbra.umbralink.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "conversation")
@Data
@NoArgsConstructor
public class Conversation extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "conversation")
    @JsonManagedReference
    @OrderBy("createdAt DESC")
    private List<Message> messages;

    private Long user1;
    private Long user2;

}
