package com.example.ht;

import com.example.ht.entries.EntryManager;

import java.io.Serializable;

public class User implements Serializable {
    String username;
    int userid;
    EntryManager EM;

    public User(String n, int id) {
        username = n;
        userid = id;
        EM = new EntryManager();
    }

    public String getUsername(){
        return username;
    }

    public int getUserid() {
        return userid;
    }

    public EntryManager getEM() {return EM;}

    @Override
    public String toString() {
        return username;
    }
}
