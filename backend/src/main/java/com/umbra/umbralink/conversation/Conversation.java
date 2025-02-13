package com.umbra.umbralink.conversation;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.umbra.umbralink.helper.BaseModel;
import com.umbra.umbralink.message.Message;
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
    private Long id;

    @OneToMany(mappedBy = "conversation")
    @JsonManagedReference
    @OrderBy("createdAt ASC")
    private List<Message> messages;

    private Long user1;
    private Long user2;

}
