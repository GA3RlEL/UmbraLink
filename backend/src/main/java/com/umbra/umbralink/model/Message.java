package com.umbra.umbralink.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.umbra.umbralink.model.enums.MessageType;
import com.umbra.umbralink.model.enums.MessageState;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
public class Message extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    @Enumerated(EnumType.STRING)
    private MessageState state;
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    @JsonBackReference
    private Conversation conversation;

    @Column(nullable = false, name = "sender_id")
    private String senderId;
    @Column(nullable = false, name = "receiver_id")
    private String receiverId;

}
