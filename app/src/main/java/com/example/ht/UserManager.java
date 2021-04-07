package com.example.ht;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

public class UserManager {
    private ArrayList<User> user_array;
    private int idCounter = 0;

    //Singleton:
    private static UserManager UM = new UserManager();
    private UserManager() {
        user_array = new ArrayList<>();
        readfile();
    }
    public static UserManager getInstance() {return UM;}


    public void addUser(String name) {
        idCounter ++;
        User user = new User(name, idCounter);
        user_array.add(user);


        writefile(name, idCounter);
    }

    public void readfile() {
        //read all users in file as usermanager is created
        // add them to arraylist
    }

    public void writefile(String username, int userID) {
        //write new created user into file
        final String xmlFile = "users";
        String userid = String.valueOf(userID);

        try {
            //FileOutputStream fos = new  FileOutputStream("users.xml");
            FileOutputStream fileos= ContextProvider.getContext().openFileOutput(xmlFile, Context.MODE_PRIVATE);
            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);

            xmlSerializer.startTag(null, "userData");
                xmlSerializer.startTag(null, "userName");
                    xmlSerializer.text(username);
                xmlSerializer.endTag(null, "userName");

                xmlSerializer.startTag(null,"userid");
                    xmlSerializer.text(userid);
                xmlSerializer.endTag(null, "userid");
            xmlSerializer.endTag(null, "userData");

            xmlSerializer.endDocument();

            xmlSerializer.flush();
            String dataWrite = writer.toString();
            fileos.write(dataWrite.getBytes());
            fileos.close();
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void deleteUser(){}

    public ArrayList<User> getUser_array() {
        return user_array;
    }

//    public User getUser(){
//        return User;
//    }

}



