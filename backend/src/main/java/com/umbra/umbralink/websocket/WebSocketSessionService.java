package com.umbra.umbralink.websocket;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class WebSocketSessionService {
    private final Map<String, Long> sessionUserMap = new HashMap<>();
    private final Map<Long, AtomicInteger> userSessionCount = new HashMap<>();

    public void addSession(String sessionId, Long userId)
    {
        sessionUserMap.put(sessionId,userId);
        userSessionCount.putIfAbsent(userId, new AtomicInteger(0));
        userSessionCount.get(userId).incrementAndGet();
    }

    public Long removeSession(String sessionId) {
        Long userId = sessionUserMap.remove(sessionId);
        if (userId != null) {
            AtomicInteger count = userSessionCount.get(userId);
            if (count != null) {
                int remainingSessions = count.decrementAndGet();
                if (remainingSessions <= 0) {
                    userSessionCount.remove(userId);
                    return userId;
                }
            }
        }
        return null;
    }
}
