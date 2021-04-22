package com.example.ht;

import android.content.Context;
import org.w3c.dom.Element;
import android.util.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class UserManager implements Serializable {
    private ArrayList<User> user_array;
    private int idCounter = 0;
    final String xmlFile = "users.xml";

    //Singleton:
    private static UserManager UM = new UserManager();
    private UserManager() {
        user_array = new ArrayList<>();

        // add all users to ArrayList
        readFile();
    }
    public static UserManager getInstance() {return UM;}


    public void addUser(String name) {
        idCounter ++;
        User user = new User(name, idCounter, null);
        user_array.add(user);

        System.out.println("Created user " + name);

        if (idCounter==1) {
            writeFile(name);
        } else {
            //update the user file
            appendUser(name, idCounter);
        }
    }


    public void readFile() {
        //read all users in file users.xml as UserManager is created
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            InputStream istream = ContextProvider.getContext().openFileInput(xmlFile);
            Document doc1 = builder.parse(istream);

            System.out.println(doc1.getDocumentElement().getNodeName());

            NodeList nList = doc1.getDocumentElement().getElementsByTagName("user");

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

            if (node.getNodeType() == node.ELEMENT_NODE) {
                Element element = (Element) node;
                System.out.println("getting tags");

                user_array.add(new User(
                        element.getElementsByTagName("userName").item(0).getTextContent(),
                        Integer.parseInt(element.getElementsByTagName("userId").item(0).getTextContent()),
                        element.getElementsByTagName("EntryManager")
                ));
                //keep count of users in the file
                idCounter++;
            }
        }
    }

    public void appendUser(String username, int userID) {
        try {
            String filepath = "/data/data/com.example.ht/files/users.xml";
            DocumentBuilderFactory docFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFact.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);

            // root element
            Node userData = doc.getFirstChild();

            //create new user
            Element user = doc.createElement("user");

            Element userName = doc.createElement("username");
            userName.appendChild(doc.createTextNode(username));
            user.appendChild(userName);

            Element userid = doc.createElement("userId");
            userid.appendChild(doc.createTextNode(Integer.toString(userID)));
            user.appendChild(userid);

            Element man = doc.createElement("EntryManager");
            user.appendChild(man);

            //add user to file
            userData.appendChild(user);

            System.out.println("added to file user " + username);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFile(String username) {
        //Write a new created user into new file users.xml
        String userId = "1";

        try {
            FileOutputStream fileos= ContextProvider.getContext().openFileOutput(xmlFile, Context.MODE_PRIVATE);
            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

            xmlSerializer.startTag(null, "userData");

            // add new user
            xmlSerializer.startTag(null, "user");

            xmlSerializer.startTag(null, "userName");
            xmlSerializer.text(username);
            xmlSerializer.endTag(null, "userName");

            xmlSerializer.startTag(null, "userId");
            xmlSerializer.text(userId);
            xmlSerializer.endTag(null, "userId");

            xmlSerializer.startTag(null, "EntryManager");
            xmlSerializer.endTag(null, "EntryManager");

            //new user has no entries yet
            xmlSerializer.endTag(null, "user");

            xmlSerializer.endTag(null, "userData");

            xmlSerializer.endDocument();

            xmlSerializer.flush();
            String dataWrite = writer.toString();
            fileos.write(dataWrite.getBytes());
            fileos.close();

            System.out.println("Saved to new file: " + username);
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



