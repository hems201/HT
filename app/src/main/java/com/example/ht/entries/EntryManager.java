package com.example.ht.entries;

import java.util.ArrayList;

public class EntryManager {
    //similar structure to UserManager
    //different lists for different types of entries?
    Integer entryID = 0;

    private ArrayList<Entry> entryArray;

    //singleton
    private static EntryManager entryManager = new EntryManager();
    public static EntryManager getInstance() {
        return entryManager;
    }

    private EntryManager() {
        entryArray = new ArrayList<Entry>();

    }

    //-------------------------------------------------------------------------//

    public void readEntries() {
        //return all entries
    }

    public void getEntry(String entryID) {
        //search and return a specific entry
    }

    public void addEntry() {
        entryID++;
        //save new entry to entryArray
        // takes either travelValues ArrayList or individual integers
    }

    public void deleteEntry(String entryID) {
        //optional
    }

}
