package com.example.ht;

import java.util.ArrayList;

public class UserManager {
    private ArrayList<User> user_array;

    //Singleton:
    private static UserManager UM = new UserManager();
    private UserManager() {}
    public static UserManager getInstance() {return UM;}


    public void addUser() {}
    public void deleteUser(){}

//    public User getUser(){
//        return User;
//    }

}



