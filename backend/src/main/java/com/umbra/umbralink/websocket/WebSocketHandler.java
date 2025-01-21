package com.umbra.umbralink.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umbra.umbralink.dto.conversationData.ConversationMessageSaveDto;
import com.umbra.umbralink.model.Message;
import com.umbra.umbralink.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class WebSocketHandler extends TextWebSocketHandler {
    private static final Set<WebSocketSession> sessions = new HashSet<>();

    @Autowired
    private ConversationService conversationService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                try {
                    webSocketSession.sendMessage(message);
                    ObjectMapper objectMapper = new ObjectMapper();
                    System.out.println(message.toString());
                    System.out.println(message.getPayload());
                    ConversationMessageSaveDto messageMapped = objectMapper.readValue(message.getPayload(),
                            ConversationMessageSaveDto.class);
//                    conversationService.saveConversation(messageMapped);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
