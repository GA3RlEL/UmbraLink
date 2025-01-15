package com.umbra.umbralink.repository;

import com.umbra.umbralink.model.Conversation;
import com.umbra.umbralink.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Integer> {
}
