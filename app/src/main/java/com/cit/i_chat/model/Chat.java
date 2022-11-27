package com.cit.i_chat.model;

public class Chat {
    String senderId;
    String receiverId;
    String message;
    String timesInMls;
    String chatKey;

    public Chat(String senderId, String receiverId, String message, String timesInMls, String chatKey) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.timesInMls = timesInMls;
        this.chatKey = chatKey;
    }

    public Chat() {
    }

    public String getChatKey() {
        return chatKey;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getMessage() {
        return message;
    }

    public String getTimesInMls() {
        return timesInMls;
    }
}
