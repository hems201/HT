package com.example.ht;

import com.example.ht.entries.EntryManager;

import java.io.Serializable;

public class User implements Serializable {
    String username;
    int userid;
    private static EntryManager EM;

    public User(String n, int id) {
        System.out.println("USER CREATED");
        username = n;
        userid = id;
        EM = new EntryManager();
        System.out.println("EM CREATED");
    }

    public String getUsername(){
        return username;
    }

    public int getUserid() {
        return userid;
    }

    public static EntryManager getEM() {
        System.out.println("EM RETURNED");
        return EM;}

    @Override
    public String toString() {
        return username;
    }
}
