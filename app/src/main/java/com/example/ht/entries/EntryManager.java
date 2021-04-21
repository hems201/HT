package com.example.ht.entries;

import java.io.Serializable;
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

    public void addEntry(int travelType, ArrayList<Integer> travelValues) {
        //save new entry to correct entryArray
        // takes travelValues ArrayList
        idCounter++;

        if (travelType == 1) {
            System.out.println("adding car entry");
            carEntryArray.add(new CarEntry(travelValues, idCounter));
        } else if (travelType == 2) {
            System.out.println("adding flight entry");
            flightEntryArray.add(new FlightEntry(travelValues, idCounter));
        } else if (travelType == 3) {
            System.out.println("adding public entry");
            publicEntryArray.add(new PublicEntry(travelValues, idCounter));
        }
    }

    public void deleteEntry(String entryID) {
        //optional
    }

}
