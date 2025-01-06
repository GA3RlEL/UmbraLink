package com.umbra.umbralink.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umbra.umbralink.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
