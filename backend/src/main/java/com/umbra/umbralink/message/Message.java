package com.umbra.umbralink.message;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.umbra.umbralink.conversation.Conversation;
import com.umbra.umbralink.helper.BaseModel;
import com.umbra.umbralink.enums.MessageType;
import com.umbra.umbralink.enums.MessageState;
import com.umbra.umbralink.user.UserEntity;
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
