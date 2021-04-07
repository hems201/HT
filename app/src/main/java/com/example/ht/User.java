package com.example.ht;

public class User {
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
