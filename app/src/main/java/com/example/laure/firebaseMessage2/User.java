package com.example.laure.firebaseMessage2;

/**
 * Created by Laure on 04/12/2017.
 */

public class User {

    private String name;
    private String email;

    public User(String name, String email){
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
