package com.example.ht;

import java.util.ArrayList;

public class UserManager {
    private ArrayList<User> user_array;

    //Singleton:
    private static UserManager UM = new UserManager();
    private UserManager() {
        user_array = new ArrayList<>();
    }
    public static UserManager getInstance() {return UM;}


    public void addUser(String name) {
        User user = new User(name);
        user_array.add(user);

        //TODO tiedostoon kirjoitus
    }

    public void deleteUser(){}

    public ArrayList<User> getUser_array() {
        return user_array;
    }

//    public User getUser(){
//        return User;
//    }

}



