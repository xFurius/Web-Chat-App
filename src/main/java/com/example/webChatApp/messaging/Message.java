package com.example.webChatApp.messaging;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String receiver;
    private String content;
    private String sender;
    private String conversationID; 

    public Message(String receiver, String content, String sender, String conversationID){
        this.receiver = receiver;
        this.content = content;
        this.sender = sender;
        this.conversationID = conversationID;
    }
}
