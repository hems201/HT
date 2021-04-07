package com.example.ht;

import java.io.Serializable;

public class User implements Serializable {
    String username;
    int userid;

    public User(String n, int id) {
        username = n;
        userid = id;
    }

    public String getUsername(){
        return username;
    }

    public int getUserid() {
        return userid;
    }

    @Override
    public String toString() {
        return username;
    }
}
