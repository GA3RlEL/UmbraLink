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
    private MessageState state = MessageState.SENT;
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    @JsonBackReference
    private Conversation conversation;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonBackReference
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    @JsonBackReference
    private UserEntity receiver;

}
