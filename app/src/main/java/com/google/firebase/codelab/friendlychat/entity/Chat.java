package com.google.firebase.codelab.friendlychat.entity;

public class Chat {
    private String id;
    private String name;
    private Message message;

    public Chat() {
    }

    public Chat(String id, String name, Message message) {
        this.id = id;
        this.name = name;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", message=" + message +
                '}';
    }
}
