package com.example.ht;

import java.util.ArrayList;

public class UserManager {
    private ArrayList<User> user_array;
    private int idCounter = 0;

    //Singleton:
    private static UserManager UM = new UserManager();
    private UserManager() {
        
        user_array = new ArrayList<>();
    }
    public static UserManager getInstance() {return UM;}


    public void addUser(String name) {
        idCounter ++;
        User user = new User(name, idCounter);
        user_array.add(user);


        writefile();
    }

    public void readfile() {
        //read all users in file as usermanager is created
        // add them to arraylist
    }

    public void writefile() {
        //write new created user into file
    }

    public void deleteUser(){}

    public ArrayList<User> getUser_array() {
        return user_array;
    }

//    public User getUser(){
//        return User;
//    }

}



