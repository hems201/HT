package com.example.ht;

import android.content.Context;
import android.renderscript.Element;
import android.util.Xml;
import android.view.View;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class UserManager {
    private ArrayList<User> user_array;
    private int idCounter = 0;
    final String xmlFile = "users.xml";

    //Singleton:
    private static UserManager UM = new UserManager();
    private UserManager() {
        user_array = new ArrayList<>();

        // add all users to ArrayList
        readfile();
    }
    public static UserManager getInstance() {return UM;}


    public void addUser(String name) {
        idCounter ++;
        User user = new User(name, idCounter);
        user_array.add(user);

        System.out.println("Created user " + name);

        writefile(name, idCounter);
    }

    public void readfile() {
        //read all users in file as usermanager is created

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            InputStream istream = ContextProvider.getContext().openFileInput(xmlFile);
            Document doc1 = builder.parse(istream);

            System.out.println(doc1.getDocumentElement().getNodeName());

            NodeList nList = doc1.getDocumentElement().getElementsByTagName("userData");

            // add them to arraylist
            parseNode(nList);

            istream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }

    private void parseNode(NodeList nList) {
        // Add users into the ArrayList
        for (int i=0; i<nList.getLength(); i++) {
            Node node = nList.item(i);
            System.out.println(node.getNodeName());

            /*
            if (node.getNodeType() == node.ELEMENT_NODE) {
                Element element = (Element) node;

                user_array.add(new User(
                        element.getElementsByTagName("userName").item(0).getTextContent(),
                        element.getElementsByTagName("userId").item(0).getTextContent()
                ));
                //keep count of users in the file
                idCounter++;
            }

             */

        }
    }

    public void writefile(String username, int userID) {
        //write new created user into file
        String userId = String.valueOf(userID);

        try {
            FileOutputStream fileos= ContextProvider.getContext().openFileOutput(xmlFile, Context.MODE_PRIVATE);
            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);

            xmlSerializer.startTag(null, "userData");
                xmlSerializer.startTag(null, "userName");
                    xmlSerializer.text(username);
                xmlSerializer.endTag(null, "userName");

                xmlSerializer.startTag(null,"userId");
                    xmlSerializer.text(userId);
                xmlSerializer.endTag(null, "userId");
            xmlSerializer.endTag(null, "userData");

            xmlSerializer.endDocument();

            xmlSerializer.flush();
            String dataWrite = writer.toString();
            fileos.write(dataWrite.getBytes());
            fileos.close();

            System.out.println("Saved to file: " + username);
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

    public void deleteUser(){
        // delete from array and file
    }

    public ArrayList<User> getUser_array() {
        return user_array;
    }

//    public User getUser(){
//        return User;
//    }

}



