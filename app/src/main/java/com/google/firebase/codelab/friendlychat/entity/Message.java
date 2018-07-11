package com.google.firebase.codelab.friendlychat.entity;

public class Message {

    private String messageId;
    private String userId;
    private String text;
    private String name;
    private String photoUrl;
    private String imageUrl;

    public Message() {
    }

    public Message(String messageId, String userId, String text, String name, String photoUrl, String imageUrl) {
        this.messageId = messageId;
        this.userId = userId;
        this.text = text;
        this.name = name;
        this.photoUrl = photoUrl;
        this.imageUrl = imageUrl;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getText() {
        return text;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId='" + messageId + '\'' +
                ", userId='" + userId + '\'' +
                ", text='" + text + '\'' +
                ", name='" + name + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
