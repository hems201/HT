package com.example.ht;

public class User {
    String username;
    String userid; //String vai int parempi?

    public User(String n) {
        username = n;
        //userid = id;
    }

    public String getUsername(){
        return username;
    }

    public String getUserid() {
        return userid;
    }

//    @Override
//    public toString() {
//
//    }
}
