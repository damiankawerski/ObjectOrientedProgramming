package org.example;

public class Message {
    public MessageType type;
    public String content;
    public String username;

    public Message() {}

    public Message(MessageType type, String content) {
        this.type = type;
        this.content = content;
    }

    public Message(MessageType type, String content, String username) {
        this.type = type;
        this.content = content;
        this.username = username;
    }
}
