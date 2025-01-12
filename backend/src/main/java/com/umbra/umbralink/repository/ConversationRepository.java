package com.umbra.umbralink.repository;

import com.umbra.umbralink.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Integer> {
}
