package com.example.ht.entries;

import android.widget.Toast;

import com.example.ht.ContextProvider;
import com.example.ht.User;

import java.io.File;
import java.io.Serializable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class EntryManager implements Serializable {
    //similar structure to UserManager
    //different lists for different types of entries

    private ArrayList<PublicEntry> publicEntryArray;
    private ArrayList<FlightEntry> flightEntryArray;
    private ArrayList<CarEntry> carEntryArray;

    String path = ContextProvider.getContext().getFilesDir().getAbsolutePath();
    File file = new File(path + "/users.xml");

    private int idCounter = 0;

    public EntryManager() {
        publicEntryArray = new ArrayList<PublicEntry>();
        flightEntryArray = new ArrayList<FlightEntry>();
        carEntryArray = new ArrayList<CarEntry>();
    }

    //-------------------------------------------------------------------------//

    public ArrayList<PublicEntry> getPublicArray() {
        return publicEntryArray;
    }
    public ArrayList<CarEntry> getCarEntryArray() {
        return carEntryArray;
    }
    public ArrayList<FlightEntry> getFlightEntryArray() {
        return flightEntryArray;
    }

    public void addEntry(int travelType, ArrayList<Integer> travelValues, int maybeId, String date, ArrayList<Double> coList, int userId) {
        //save new entry to correct entryArray and userdata file
        // takes coList, where depending on entry type has either one or multiple variables

        // id included if entry is from file
        if (maybeId==0) {
            idCounter++;
        } else {
            idCounter=maybeId;
        }

        if (travelType == 1) {
            System.out.println("adding car entry");
            if (coList!=null) {
                //old entry, add to array
                carEntryArray.add(new CarEntry(travelValues, idCounter, date, coList.get(0)));
            } else {
                // this is a new entry -> add to array
                carEntryArray.add(new CarEntry(travelValues, idCounter, date, null));
                //get that last entry and add it to file
                appendCarEntry(userId, carEntryArray.get(carEntryArray.size()-1));
                //Show total CO2 with toast
                Toast.makeText(ContextProvider.getContext(),"Car trip added. Total CO2: " + Math.round(carEntryArray.get(carEntryArray.size()-1).getTotalCO())+"kg", Toast.LENGTH_LONG).show();
            }
        } else if (travelType == 2) {
            System.out.println("adding flight entry");
            if (coList!=null) {
                flightEntryArray.add(new FlightEntry(travelValues, idCounter, date, coList.get(0)));
            } else {
                // this is a new entry
                flightEntryArray.add(new FlightEntry(travelValues, idCounter, date, null));
                appendFlightEntry(userId, flightEntryArray.get(flightEntryArray.size()-1));
                //Show total CO2 with toast
                Toast.makeText(ContextProvider.getContext(),"Flight added. Total CO2: " + Math.round(flightEntryArray.get(flightEntryArray.size()-1).getTotalCO())+"kg", Toast.LENGTH_LONG).show();
            }
        } else if (travelType == 3) {
            System.out.println("adding public entry");
            // has multiple CO values in coList
            if (coList!=null) {
                publicEntryArray.add(new PublicEntry(travelValues, idCounter, date, coList));
            } else {
                // this is a new entry
                publicEntryArray.add(new PublicEntry(travelValues, idCounter, date, null));
                appendPublicEntry(userId, publicEntryArray.get(publicEntryArray.size()-1));
                //Show total CO2 with toast
                Toast.makeText(ContextProvider.getContext(),"Public transport trip added. Total CO2: " + Math.round(publicEntryArray.get(publicEntryArray.size()-1).getTotalCO())+"kg", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void appendCarEntry(int userId, CarEntry newEntry) {
        // add new CarEntry to userdata file
        //http://www.java2s.com/Tutorials/Java/XML_HTML_How_to/DOM/Append_a_node_to_an_existing_XML_file.htm
        try {
            DocumentBuilderFactory docFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFact.newDocumentBuilder();
            Document doc = docBuilder.parse(file);

            // get correct user
            NodeList userList = doc.getElementsByTagName("user");
            Node thisUser;
            for (int i=0; i<userList.getLength(); i++) {
                //check which userId matches
                if (userList.item(i).getChildNodes().item(1).getTextContent().equals(Integer.toString(userId))) {
                    thisUser = userList.item(i);
                    System.out.println("user found; adding entry");

                    //get the EntryManager of this user
                    Node manager = thisUser.getChildNodes().item(2);

                    //create new entry
                    Element entry = doc.createElement("CarEntry");

                    Element e = doc.createElement("EntryID");
                    e.appendChild(doc.createTextNode(Integer.toString(newEntry.getEntryID())));
                    entry.appendChild(e);

                    e = doc.createElement("Date");
                    e.appendChild(doc.createTextNode(newEntry.getDate()));
                    entry.appendChild(e);

                    e = doc.createElement("TotalCO");
                    e.appendChild(doc.createTextNode(String.valueOf(newEntry.getTotalCO())));
                    entry.appendChild(e);

                    e = doc.createElement("CarYear");
                    e.appendChild(doc.createTextNode(String.valueOf(newEntry.getCarYear())));
                    entry.appendChild(e);

                    e = doc.createElement("Km");
                    e.appendChild(doc.createTextNode(String.valueOf(newEntry.getKm())));
                    entry.appendChild(e);

                    e = doc.createElement("Passengers");
                    e.appendChild(doc.createTextNode(String.valueOf(newEntry.getPassengers())));
                    entry.appendChild(e);

                    // add entry under manager in file
                    manager.appendChild(entry);

                    // ??
                    TransformerFactory tff = TransformerFactory.newInstance();
                    Transformer tf = tff.newTransformer();
                    DOMSource source = new DOMSource(doc);
                    StreamResult res = new StreamResult(file);
                    tf.transform(source, res);

                    System.out.println("added new car entry to file");
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public void appendFlightEntry(int userId, FlightEntry newEntry) {
        // add new FlightEntry do userdata file
        try {
            DocumentBuilderFactory docFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFact.newDocumentBuilder();
            Document doc = docBuilder.parse(file);

            // get right user
            NodeList userList = doc.getElementsByTagName("user");
            Node thisUser;
            for (int i=0; i<userList.getLength(); i++) {
                //check which userId matches
                if (userList.item(i).getChildNodes().item(1).getTextContent().equals(Integer.toString(userId))) {
                    thisUser = userList.item(i);
                    System.out.println("user found; adding entry");

                    //get the EntryManager of this user
                    Node manager = thisUser.getChildNodes().item(2);

                    //create new entry
                    Element entry = doc.createElement("FlightEntry");

                    Element e = doc.createElement("EntryID");
                    e.appendChild(doc.createTextNode(Integer.toString(newEntry.getEntryID())));
                    entry.appendChild(e);

                    e = doc.createElement("Date");
                    e.appendChild(doc.createTextNode(newEntry.getDate()));
                    entry.appendChild(e);

                    e = doc.createElement("TotalCO");
                    e.appendChild(doc.createTextNode(String.valueOf(newEntry.getTotalCO())));
                    entry.appendChild(e);

                    e = doc.createElement("Fin");
                    e.appendChild(doc.createTextNode(String.valueOf(newEntry.getPlaneFin())));
                    entry.appendChild(e);

                    e = doc.createElement("Ca");
                    e.appendChild(doc.createTextNode(String.valueOf(newEntry.getPlaneCa())));
                    entry.appendChild(e);

                    e = doc.createElement("Eu");
                    e.appendChild(doc.createTextNode(String.valueOf(newEntry.getPlaneEu())));
                    entry.appendChild(e);

                    e = doc.createElement("Tra");
                    e.appendChild(doc.createTextNode(String.valueOf(newEntry.getPlaneTra())));
                    entry.appendChild(e);

                    // add entry under manager in file
                    manager.appendChild(entry);

                    TransformerFactory tff = TransformerFactory.newInstance();
                    Transformer tf = tff.newTransformer();
                    DOMSource source = new DOMSource(doc);
                    StreamResult res = new StreamResult(file);
                    tf.transform(source, res);

                    System.out.println("added new flight entry to file");
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public void appendPublicEntry(int userId, PublicEntry newEntry) {
        // add new PublicEntry do userdata file
        try {
            DocumentBuilderFactory docFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFact.newDocumentBuilder();
            Document doc = docBuilder.parse(file);

            // get right user
            NodeList userList = doc.getElementsByTagName("user");
            Node thisUser;
            for (int i=0; i<userList.getLength(); i++) {
                //check which userId matches
                if (userList.item(i).getChildNodes().item(1).getTextContent().equals(Integer.toString(userId))) {
                    thisUser = userList.item(i);
                    System.out.println("user found; adding entry");

                    //get the EntryManager of this user
                    Node manager = thisUser.getChildNodes().item(2);

                    //create new entry
                    Element entry = doc.createElement("PublicEntry");

                    Element e = doc.createElement("EntryID");
                    e.appendChild(doc.createTextNode(Integer.toString(newEntry.getEntryID())));
                    entry.appendChild(e);

                    e = doc.createElement("Date");
                    e.appendChild(doc.createTextNode(newEntry.getDate()));
                    entry.appendChild(e);

                    e = doc.createElement("TotalCO");
                    e.appendChild(doc.createTextNode(String.valueOf(newEntry.getTotalCO())));
                    entry.appendChild(e);

                    e = doc.createElement("BusCO");
                    e.appendChild(doc.createTextNode(String.valueOf(newEntry.getBusCO())));
                    entry.appendChild(e);

                    e = doc.createElement("TrainCO");
                    e.appendChild(doc.createTextNode(String.valueOf(newEntry.getTrainCO())));
                    entry.appendChild(e);

                    e = doc.createElement("OtherCO");
                    e.appendChild(doc.createTextNode(String.valueOf(newEntry.getOtherCO())));
                    entry.appendChild(e);

                    e = doc.createElement("LBus");
                    e.appendChild(doc.createTextNode(String.valueOf(newEntry.getlBus())));
                    entry.appendChild(e);

                    e = doc.createElement("SBus");
                    e.appendChild(doc.createTextNode(String.valueOf(newEntry.getsBus())));
                    entry.appendChild(e);

                    e = doc.createElement("LTrain");
                    e.appendChild(doc.createTextNode(String.valueOf(newEntry.getlTrain())));
                    entry.appendChild(e);

                    e = doc.createElement("STrain");
                    e.appendChild(doc.createTextNode(String.valueOf(newEntry.getsTrain())));
                    entry.appendChild(e);

                    e = doc.createElement("Metro");
                    e.appendChild(doc.createTextNode(String.valueOf(newEntry.getMetro())));
                    entry.appendChild(e);

                    e = doc.createElement("Tram");
                    e.appendChild(doc.createTextNode(String.valueOf(newEntry.getTram())));
                    entry.appendChild(e);

                    // add entry under manager in file
                    manager.appendChild(entry);

                    TransformerFactory tff = TransformerFactory.newInstance();
                    Transformer tf = tff.newTransformer();
                    DOMSource source = new DOMSource(doc);
                    StreamResult res = new StreamResult(file);
                    tf.transform(source, res);

                    System.out.println("added new public entry to file");
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
