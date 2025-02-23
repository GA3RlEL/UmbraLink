package com.umbra.umbralink.dto.conversationData;

import com.umbra.umbralink.enums.MessageState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReadMessageDto {
    private Long messageId;
    private Long conversationId;
    private MessageState state;
    private LocalDateTime updateTime;
}
