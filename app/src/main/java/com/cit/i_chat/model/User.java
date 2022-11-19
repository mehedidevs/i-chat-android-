package com.cit.i_chat.model;

public class User {

    String user_name,user_email,user_password,user_profile;

    public User(String user_name, String user_email, String user_password, String user_profile) {
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_profile = user_profile;
    }

    public User() {
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public String getUser_profile() {
        return user_profile;
    }
}
