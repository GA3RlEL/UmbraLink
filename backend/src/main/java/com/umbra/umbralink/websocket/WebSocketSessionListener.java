package com.umbra.umbralink.websocket;

import com.umbra.umbralink.dto.UserStatusDto;
import com.umbra.umbralink.model.enums.UserStatus;
import com.umbra.umbralink.security.jwt.JwtService;
import com.umbra.umbralink.service.UserService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketSessionListener {

    private final JwtService jwtService;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;
    private final WebSocketSessionService webSocketSessionService;

    public WebSocketSessionListener(JwtService jwtService, SimpMessagingTemplate messagingTemplate, UserService userService, WebSocketSessionService webSocketSessionService) {
        this.jwtService = jwtService;
        this.messagingTemplate = messagingTemplate;
        this.userService = userService;
        this.webSocketSessionService = webSocketSessionService;
    }

    @EventListener
    public void handleSessionConnect(SessionConnectEvent event)
    {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String token = accessor.getFirstNativeHeader("Authorization");
        if(token!=null)
        {
            var userId = jwtService.extractId(token);
            String sessionId = accessor.getSessionId();
            webSocketSessionService.addSession(sessionId,userId);
            UserStatusDto dto = userService.changeUserStatus(userId, UserStatus.ONLINE);
            messagingTemplate.convertAndSend("/status", dto);

        }
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event)
    {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        Long userId = webSocketSessionService.removeSession(sessionId);
        if(userId!=null)
        {
            UserStatusDto dto = userService.changeUserStatus(userId,UserStatus.OFFLINE);
            messagingTemplate.convertAndSend("/status",dto);
        }
    }
}
