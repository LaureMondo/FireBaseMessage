package com.example.laure.firebaseMessage2;

/**
 * Represent a single message sended by an user
 *
 * Created by Hugo Gresse on 26/11/2017.
 */

public class Message {

    public String content;
    public String userName;
    public Long timestamp;
    public String userMail;

    public Message() {
        // Empty constructor for Firebase
    }

    public Message(String content, String userName, String userMail, Long timestamp) {
        this.content = content;
        this.userName = userName;
        this.timestamp = timestamp;
        this.userMail = userMail;
    }
}
