package com.blackjack.util;

public class Message {
    private final String messageType;
    private final String content;


    public Message(String msgType, String content) {
        this.messageType = msgType;
        this.content = content;
    }

    public String messageType() {
        return this.messageType;
    }

    public String contents() {
        return this.content;
    }
}
