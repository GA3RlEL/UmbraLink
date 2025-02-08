package com.umbra.umbralink.repository;

import com.umbra.umbralink.model.Conversation;
import com.umbra.umbralink.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Integer> {

    @Query("SELECT c FROM Conversation c " +
            "WHERE (c.user1 = :user1 AND c.user2 = :user2) " +
            "OR (c.user1 = :user2 AND c.user2 = :user1)")
    Optional<Conversation> findConversation(Long user1, Long user2);
}
