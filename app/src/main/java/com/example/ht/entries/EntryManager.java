package com.example.ht.entries;

import java.util.ArrayList;

public class EntryManager {
    //similar structure to UserManager
    //different lists for different types of entries?

    private ArrayList<PublicEntry> publicEntryArray;

    //singleton
    private static EntryManager entryManager = new EntryManager();
    public static EntryManager getInstance() {
        return entryManager;
    }

    public EntryManager() {
        publicEntryArray = new ArrayList<PublicEntry>();

    }

    //-------------------------------------------------------------------------//

    public void readEntries() {
        //return all entries
    }

    public void getEntry(String entryID) {
        //search and return a specific entry
    }

    public void addEntry() {
        //save new entry to entryArray
        // takes either travelValues ArrayList or individual integers
        publicEntryArray.add(new PublicEntry("0", 0.0,0.0));

    }

    public void deleteEntry(String entryID) {
        //optional
    }

}
