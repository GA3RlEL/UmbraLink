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

    public WebSocketSessionListener(JwtService jwtService, SimpMessagingTemplate messagingTemplate, UserService userService) {
        this.jwtService = jwtService;
        this.messagingTemplate = messagingTemplate;
        this.userService = userService;
    }

    @EventListener
    public void handleSessionConnect(SessionConnectEvent event)
    {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String token = accessor.getFirstNativeHeader("Authorization");

        System.out.println(token);
        System.out.println(event);
        var userId = jwtService.extractId(token);
        System.out.println(userId);

        UserStatusDto dto = userService.changeUserStatus(userId, UserStatus.ONLINE);
        messagingTemplate.convertAndSend("/status", dto);
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event)
    {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
//        String token = accessor.getFirstNativeHeader("Authorization");
        System.out.println(event);
    }
}
