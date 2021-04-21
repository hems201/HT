package com.example.ht;

import com.example.ht.entries.EntryManager;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    String username;
    int userid;
    EntryManager EM;

    public User(String n, int id, NodeList managerData) {
        username = n;
        userid = id;
        EM = new EntryManager();

        if (managerData!=null) {
            //parse managerData to EM
            Node node = managerData.item(0);

            if (node.getNodeType() == node.ELEMENT_NODE) {
                Element e = (Element) node;
                //classify entry type
                NodeList carList = e.getElementsByTagName("CarEntry");
                NodeList flightList = e.getElementsByTagName("FlightEntry");
                NodeList publicList = e.getElementsByTagName("PublicEntry");

                int entryID=0;
                ArrayList<Integer> travelValues = new ArrayList<Integer>();
                ArrayList<Double> coList = new ArrayList<Double>();

                // go through cars
                for (int i=0; i<carList.getLength(); i++) {
                    Node cnode = carList.item(i);

                    if (node.getNodeType() == cnode.ELEMENT_NODE) {
                        Element ce = (Element) cnode;
                        //get all entry information
                        entryID = Integer.parseInt(ce.getElementsByTagName("EntryID").item(0).getTextContent());
                        String date = ce.getElementsByTagName("Date").item(0).getTextContent();
                        Double totalCO = Double.parseDouble(ce.getElementsByTagName("TotalCO").item(0).getTextContent());
                        Integer carYear = Integer.parseInt(ce.getElementsByTagName("CarYear").item(0).getTextContent());
                        Integer km = Integer.parseInt(ce.getElementsByTagName("Km").item(0).getTextContent());
                        Integer passengers = Integer.parseInt(ce.getElementsByTagName("Passengers").item(0).getTextContent());

                        // add to respective lists
                        travelValues.add(km); travelValues.add(carYear);
                        travelValues.add(passengers); coList.add(totalCO);

                        //pass to manager
                        EM.addEntry(1, travelValues, entryID, date, coList);
                    }
                }

                //initialize lists and go through flights
                travelValues = new ArrayList<Integer>();
                coList = new ArrayList<Double>();
                for (int i=0; i<flightList.getLength(); i++) {
                    Node fnode = flightList.item(i);

                    if (node.getNodeType() == fnode.ELEMENT_NODE) {
                        Element ce = (Element) fnode;
                        //get all entry information
                        entryID = Integer.parseInt(ce.getElementsByTagName("EntryID").item(0).getTextContent());
                        String date = ce.getElementsByTagName("Date").item(0).getTextContent();
                        Double totalCO = Double.parseDouble(ce.getElementsByTagName("TotalCO").item(0).getTextContent());
                        Integer fin = Integer.parseInt(ce.getElementsByTagName("Fin").item(0).getTextContent());
                        Integer ca = Integer.parseInt(ce.getElementsByTagName("Ca").item(0).getTextContent());
                        Integer eu = Integer.parseInt(ce.getElementsByTagName("EU").item(0).getTextContent());
                        Integer tra = Integer.parseInt(ce.getElementsByTagName("Tra").item(0).getTextContent());

                        // add to respective lists
                        travelValues.add(fin); travelValues.add(eu);
                        travelValues.add(ca); travelValues.add(tra);
                        coList.add(totalCO);

                        //pass to manager
                        EM.addEntry(2, travelValues, entryID, date, coList);
                    }
                }

                //initialize lists and go through publics
                travelValues = new ArrayList<Integer>();
                coList = new ArrayList<Double>();
                for (int i=0; i<publicList.getLength(); i++) {
                    Node pnode = publicList.item(i);

                    if (node.getNodeType() == pnode.ELEMENT_NODE) {
                        Element ce = (Element) pnode;
                        //get all entry information
                        entryID = Integer.parseInt(ce.getElementsByTagName("EntryID").item(0).getTextContent());
                        String date = ce.getElementsByTagName("Date").item(0).getTextContent();
                        Double totalCO = Double.parseDouble(ce.getElementsByTagName("TotalCO").item(0).getTextContent());
                        Double busCO = Double.parseDouble(ce.getElementsByTagName("BusCO").item(0).getTextContent());
                        Double trainCO = Double.parseDouble(ce.getElementsByTagName("TrainCO").item(0).getTextContent());
                        Double otherCO = Double.parseDouble(ce.getElementsByTagName("OtherCO").item(0).getTextContent());
                        Integer lBus = Integer.parseInt(ce.getElementsByTagName("LBus").item(0).getTextContent());
                        Integer sBus = Integer.parseInt(ce.getElementsByTagName("SBus").item(0).getTextContent());
                        Integer lTrain = Integer.parseInt(ce.getElementsByTagName("LTrain").item(0).getTextContent());
                        Integer sTrain = Integer.parseInt(ce.getElementsByTagName("STrain").item(0).getTextContent());
                        Integer metro = Integer.parseInt(ce.getElementsByTagName("Metro").item(0).getTextContent());
                        Integer tram = Integer.parseInt(ce.getElementsByTagName("Tram").item(0).getTextContent());

                        // add to respective lists
                        travelValues.add(lBus); travelValues.add(sBus);
                        travelValues.add(lTrain); travelValues.add(sTrain);
                        travelValues.add(metro); travelValues.add(tram);
                        coList.add(totalCO); coList.add(busCO);
                        coList.add(trainCO); coList.add(otherCO);

                        //pass to manager
                        EM.addEntry(3, travelValues, entryID, date, coList);
                    }
                }
            }



        }
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
