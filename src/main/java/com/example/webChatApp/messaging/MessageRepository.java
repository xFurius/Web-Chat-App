package com.example.webChatApp.messaging;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, String>{
    public List<Message> findAllByConversationID(String conversationID);
}
