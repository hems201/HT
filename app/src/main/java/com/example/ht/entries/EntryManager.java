package com.example.ht.entries;

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

    public void readEntries() {
        //return all entries
    }

    public void getEntry(String entryID) {
        //
    }
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
        // takes co arraylist, where depending on entry type has either one or multiple variables
        if (maybeId==0) {
            idCounter++;
        } else {
            idCounter=maybeId;
        }

        if (travelType == 1) {
            System.out.println("adding car entry");
            if (coList!=null) {
                carEntryArray.add(new CarEntry(travelValues, idCounter, date, coList.get(0)));
            } else {
                // this is a new entry
                carEntryArray.add(new CarEntry(travelValues, idCounter, date, null));
                appendCarEntry(userId, carEntryArray.get(idCounter-1));
            }
        } else if (travelType == 2) {
            System.out.println("adding flight entry");
            if (coList!=null) {
                flightEntryArray.add(new FlightEntry(travelValues, idCounter, date, coList.get(0)));
            } else {
                // this is a new entry
                flightEntryArray.add(new FlightEntry(travelValues, idCounter, date, null));
                appendFlightEntry(userId, flightEntryArray.get(idCounter-1));
            }
        } else if (travelType == 3) {
            System.out.println("adding public entry");
            // has multiple CO values in coList
            if (coList!=null) {
                publicEntryArray.add(new PublicEntry(travelValues, idCounter, date, coList));
            } else {
                // this is a new entry
                publicEntryArray.add(new PublicEntry(travelValues, idCounter, date, null));
                appendPublicEntry(userId, publicEntryArray.get(idCounter-1));
            }
        }
    }

    public void appendCarEntry(int userId, CarEntry carEntry) {
        // add new CarEntry do userdata file
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

                    //create new entry
                    Element entry = doc.createElement("CarEntry");

                    Element entryID = doc.createElement("EntryID");
                    entryID.appendChild(doc.createTextNode(Integer.toString(carEntry.getEntryID())));
                    entry.appendChild(entryID);

                    Element entryDate = doc.createElement("Date");
                    entryDate.appendChild(doc.createTextNode(carEntry.getDate()));
                    entry.appendChild(entryDate);

                    Element entryCO = doc.createElement("TotalCO");
                    entryCO.appendChild(doc.createTextNode(String.valueOf(carEntry.getTotalCO())));
                    entry.appendChild(entryCO);

                    Element carYear = doc.createElement("CarYear");
                    carYear.appendChild(doc.createTextNode(String.valueOf(carEntry.getCarYear())));
                    entry.appendChild(carYear);

                    Element km = doc.createElement("Km");
                    km.appendChild(doc.createTextNode(String.valueOf(carEntry.getKm())));
                    entry.appendChild(km);

                    Element pass = doc.createElement("Passengers");
                    pass.appendChild(doc.createTextNode(String.valueOf(carEntry.getPassengers())));
                    entry.appendChild(pass);

                    thisUser.appendChild(entry);

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
    public void appendFlightEntry(int userId, FlightEntry flightEntry) {
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

                    //create new entry
                    Element entry = doc.createElement("FlightEntry");

                    Element entryID = doc.createElement("EntryID");
                    entryID.appendChild(doc.createTextNode(Integer.toString(flightEntry.getEntryID())));
                    entry.appendChild(entryID);

                    Element entryDate = doc.createElement("Date");
                    entryDate.appendChild(doc.createTextNode(flightEntry.getDate()));
                    entry.appendChild(entryDate);

                    Element entryCO = doc.createElement("TotalCO");
                    entryCO.appendChild(doc.createTextNode(String.valueOf(flightEntry.getTotalCO())));
                    entry.appendChild(entryCO);

                    Element f = doc.createElement("Fin");
                    f.appendChild(doc.createTextNode(String.valueOf(flightEntry.getPlaneFin())));
                    entry.appendChild(f);

                    Element km = doc.createElement("Ca");
                    km.appendChild(doc.createTextNode(String.valueOf(flightEntry.getPlaneCa())));
                    entry.appendChild(km);

                    Element eu = doc.createElement("Eu");
                    eu.appendChild(doc.createTextNode(String.valueOf(flightEntry.getPlaneEu())));
                    entry.appendChild(eu);

                    Element t = doc.createElement("Tra");
                    t.appendChild(doc.createTextNode(String.valueOf(flightEntry.getPlaneTra())));
                    entry.appendChild(t);

                    thisUser.appendChild(entry);

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
    public void appendPublicEntry(int userId, PublicEntry publicEntry) {
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

                    //create new entry
                    Element entry = doc.createElement("PublicEntry");

                    Element e = doc.createElement("EntryID");
                    e.appendChild(doc.createTextNode(Integer.toString(publicEntry.getEntryID())));
                    entry.appendChild(e);

                    Element entryDate = doc.createElement("Date");
                    entryDate.appendChild(doc.createTextNode(publicEntry.getDate()));
                    entry.appendChild(entryDate);

                    Element entryCO = doc.createElement("TotalCO");
                    entryCO.appendChild(doc.createTextNode(String.valueOf(publicEntry.getTotalCO())));
                    entry.appendChild(entryCO);

                    Element b = doc.createElement("BusCO");
                    b.appendChild(doc.createTextNode(String.valueOf(publicEntry.getBusCO())));
                    entry.appendChild(b);

                    Element t = doc.createElement("TrainCO");
                    t.appendChild(doc.createTextNode(String.valueOf(publicEntry.getTrainCO())));
                    entry.appendChild(t);

                    Element eu = doc.createElement("OtherCO");
                    eu.appendChild(doc.createTextNode(String.valueOf(publicEntry.getOtherCO())));
                    entry.appendChild(eu);

                    Element km = doc.createElement("LBus");
                    km.appendChild(doc.createTextNode(String.valueOf(publicEntry.getlBus())));
                    entry.appendChild(km);

                    Element a = doc.createElement("SBus");
                    a.appendChild(doc.createTextNode(String.valueOf(publicEntry.getsBus())));
                    entry.appendChild(a);

                    e = doc.createElement("LTrain");
                    e.appendChild(doc.createTextNode(String.valueOf(publicEntry.getlTrain())));
                    entry.appendChild(e);

                    e = doc.createElement("STrain");
                    e.appendChild(doc.createTextNode(String.valueOf(publicEntry.getsTrain())));
                    entry.appendChild(e);

                    e = doc.createElement("Metro");
                    e.appendChild(doc.createTextNode(String.valueOf(publicEntry.getMetro())));
                    entry.appendChild(e);

                    e = doc.createElement("Tram");
                    e.appendChild(doc.createTextNode(String.valueOf(publicEntry.getTram())));
                    entry.appendChild(e);

                    thisUser.appendChild(entry);

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

    public void deleteEntry(String entryID) {
        //optional
    }

}
