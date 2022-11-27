package com.cit.i_chat.model;

public class Chat {
    String senderId;
    String receiverId;
    String message;
    String timesInMls;

    public Chat(String senderId, String receiverId, String message, String timesInMls) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.timesInMls = timesInMls;
    }

    public Chat() {
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
