package com.umbra.umbralink.dto.conversationData;

import com.umbra.umbralink.model.enums.MessageState;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReadMessageDto {
    private Long messageId;
    private MessageState state;
}
