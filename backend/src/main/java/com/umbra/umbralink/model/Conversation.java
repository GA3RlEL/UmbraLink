package com.umbra.umbralink.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.Order;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "conversation")
@Data
@NoArgsConstructor
public class Conversation extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonBackReference
    private UserEntity sender;
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    @JsonBackReference
    private UserEntity recipient;

    @OneToMany(mappedBy = "conversation")
    @OrderBy("createdAt DESC")
    @JsonManagedReference
    private List<Message> messages;

}
