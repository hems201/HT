package com.example.ht.entries;

import java.util.ArrayList;

public class EntryManager {
    //similar structure to UserManager
    //different lists for different types of entries?

    private ArrayList<PublicEntry> publicEntryArray;
    private ArrayList<FlightEntry> flightEntryArray;
    private ArrayList<CarEntry> carEntryArray;

    private int idCounter = 0;

    //singleton
    private static EntryManager entryManager = new EntryManager();
    public static EntryManager getInstance() {
        return entryManager;
    }

    private EntryManager() {
        publicEntryArray = new ArrayList<PublicEntry>();
        flightEntryArray = new ArrayList<FlightEntry>();
        carEntryArray = new ArrayList<CarEntry>();

    }

    //-------------------------------------------------------------------------//

    public void readEntries() {
        //return all entries
    }

    public void getEntry(String entryID) {
        //search and return a specific entry
    }

    public void addEntry(int travelType, ArrayList<Integer> travelValues) {
        //save new entry to correct entryArray
        // takes travelValues ArrayList
        idCounter++;

        if (travelType == 1) {
            System.out.println("adding car entry");
            carEntryArray.add(new CarEntry(travelValues, idCounter));
        } else if (travelType == 2) {
            flightEntryArray.add(new FlightEntry(travelValues, idCounter));
        } else if (travelType == 3) {
            publicEntryArray.add(new PublicEntry(travelValues, idCounter));
        }
    }

    public void deleteEntry(String entryID) {
        //optional
    }

}
