package com.umbra.umbralink.dto.conversationData;

import com.umbra.umbralink.enums.MessageState;
import com.umbra.umbralink.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationMessageDto {
    private Long messageId;
    private Long conversationId;
    private Long senderId;
    private Long receiverId;
    private String content=null;
    private LocalDateTime updateTime;
    private LocalDateTime sentTime;
    private MessageState state;
    private String photoUrl=null;
    private MessageType type;
}
