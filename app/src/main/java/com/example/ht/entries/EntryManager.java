package com.example.ht.entries;

import java.io.Serializable;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.util.ArrayList;


public class EntryManager implements Serializable {
    //similar structure to UserManager
    //different lists for different types of entries

    private ArrayList<PublicEntry> publicEntryArray;
    private ArrayList<FlightEntry> flightEntryArray;
    private ArrayList<CarEntry> carEntryArray;

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

    public void addEntry(int travelType, ArrayList<Integer> travelValues, int maybeId, String date, ArrayList<Double> coList) {
        //save new entry to correct entryArray
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
                carEntryArray.add(new CarEntry(travelValues, idCounter, date, null));
            }
        } else if (travelType == 2) {
            System.out.println("adding flight entry");
            if (coList!=null) {
                flightEntryArray.add(new FlightEntry(travelValues, idCounter, date, coList.get(0)));
            } else {
                flightEntryArray.add(new FlightEntry(travelValues, idCounter, date, null));
            }

        } else if (travelType == 3) {
            System.out.println("adding public entry");
            // has multiple CO values in coList
            if (coList!=null) {
                publicEntryArray.add(new PublicEntry(travelValues, idCounter, date, coList));
            } else {
                publicEntryArray.add(new PublicEntry(travelValues, idCounter, date, null));
            }
        }
    }

    public XmlSerializer writeFile(XmlSerializer xmlSerializer) {
        //save entries into file, part of UserManager writefile()
        System.out.println("Entries are being written into file");
        try {
            for (int i=0; i<carEntryArray.size()-1; i++) {
                xmlSerializer.startTag(null, "CarEntry");
                    xmlSerializer.startTag(null, "EntryID");
                    xmlSerializer.text(Integer.toString(carEntryArray.get(i).getEntryID()));
                    xmlSerializer.endTag(null, "EntryID");

                    xmlSerializer.startTag(null, "Date");
                    xmlSerializer.text(carEntryArray.get(i).getDate());
                    xmlSerializer.endTag(null, "Date");

                    xmlSerializer.startTag(null, "TotalCO");
                    xmlSerializer.text(Double.toString(carEntryArray.get(i).getTotalCO()));
                    xmlSerializer.endTag(null, "TotalCO");

                    xmlSerializer.startTag(null, "CarYear");
                    xmlSerializer.text(Integer.toString(carEntryArray.get(i).getCarYear()));
                    xmlSerializer.endTag(null, "CarYear");

                    xmlSerializer.startTag(null, "Km");
                    xmlSerializer.text(Integer.toString(carEntryArray.get(i).getKm()));
                    xmlSerializer.endTag(null, "Km");

                    xmlSerializer.startTag(null, "Passengers");
                    xmlSerializer.text(Integer.toString(carEntryArray.get(i).getPassengers()));
                    xmlSerializer.endTag(null, "Passengers");
                xmlSerializer.endTag(null, "CarEntry");
            }
            for (int i=0; i<flightEntryArray.size()-1; i++) {
                xmlSerializer.startTag(null, "FlightEntry");
                    xmlSerializer.startTag(null, "EntryID");
                    xmlSerializer.text(Integer.toString(flightEntryArray.get(i).getEntryID()));
                    xmlSerializer.endTag(null, "EntryID");

                    xmlSerializer.startTag(null, "Date");
                    xmlSerializer.text(flightEntryArray.get(i).getDate());
                    xmlSerializer.endTag(null, "Date");

                    xmlSerializer.startTag(null, "TotalCO");
                    xmlSerializer.text(Double.toString(flightEntryArray.get(i).getTotalCO()));
                    xmlSerializer.endTag(null, "TotalCO");

                    xmlSerializer.startTag(null, "Fin");
                    xmlSerializer.text(Integer.toString(flightEntryArray.get(i).getPlaneFin()));
                    xmlSerializer.endTag(null, "Fin");

                    xmlSerializer.startTag(null, "Ca");
                    xmlSerializer.text(Integer.toString(flightEntryArray.get(i).getPlaneCa()));
                    xmlSerializer.endTag(null, "Ca");

                    xmlSerializer.startTag(null, "EU");
                    xmlSerializer.text(Integer.toString(flightEntryArray.get(i).getPlaneEu()));
                    xmlSerializer.endTag(null, "EU");

                    xmlSerializer.startTag(null, "Tra");
                    xmlSerializer.text(Integer.toString(flightEntryArray.get(i).getPlaneTra()));
                    xmlSerializer.endTag(null, "Tra");
                xmlSerializer.endTag(null, "FlightEntry");
            }
            for (int i=0; i<publicEntryArray.size()-1; i++) {
                xmlSerializer.startTag(null, "PublicEntry");
                    xmlSerializer.startTag(null, "EntryID");
                    xmlSerializer.text(Integer.toString(publicEntryArray.get(i).getEntryID()));
                    xmlSerializer.endTag(null, "EntryID");

                    xmlSerializer.startTag(null, "Date");
                    xmlSerializer.text(publicEntryArray.get(i).getDate());
                    xmlSerializer.endTag(null, "Date");

                    xmlSerializer.startTag(null, "TotalCO");
                    xmlSerializer.text(Double.toString(publicEntryArray.get(i).getTotalCO()));
                    xmlSerializer.endTag(null, "TotalCO");

                    xmlSerializer.startTag(null, "BusCO");
                    xmlSerializer.text(Double.toString(publicEntryArray.get(i).getBusCO()));
                    xmlSerializer.endTag(null, "BusCO");

                    xmlSerializer.startTag(null, "TrainCO");
                    xmlSerializer.text(Double.toString(publicEntryArray.get(i).getTrainCO()));
                    xmlSerializer.endTag(null, "TrainCO");

                    xmlSerializer.startTag(null, "OtherCO");
                    xmlSerializer.text(Double.toString(publicEntryArray.get(i).getOtherCO()));
                    xmlSerializer.endTag(null, "OtherCO");

                    xmlSerializer.startTag(null, "LBus");
                    xmlSerializer.text(Integer.toString(publicEntryArray.get(i).getlBus()));
                    xmlSerializer.endTag(null, "LBus");

                    xmlSerializer.startTag(null, "SBus");
                    xmlSerializer.text(Integer.toString(publicEntryArray.get(i).getsBus()));
                    xmlSerializer.endTag(null, "SBus");

                    xmlSerializer.startTag(null, "LTrain");
                    xmlSerializer.text(Integer.toString(publicEntryArray.get(i).getlTrain()));
                    xmlSerializer.endTag(null, "LTrain");

                    xmlSerializer.startTag(null, "STrain");
                    xmlSerializer.text(Integer.toString(publicEntryArray.get(i).getsTrain()));
                    xmlSerializer.endTag(null, "STrain");

                    xmlSerializer.startTag(null, "Metro");
                    xmlSerializer.text(Integer.toString(publicEntryArray.get(i).getMetro()));
                    xmlSerializer.endTag(null, "Metro");

                    xmlSerializer.startTag(null, "Tram");
                    xmlSerializer.text(Integer.toString(publicEntryArray.get(i).getTram()));
                    xmlSerializer.endTag(null, "Tram");

                xmlSerializer.endTag(null, "PublicEntry");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xmlSerializer;
    }

    public void deleteEntry(String entryID) {
        //optional
    }

}
