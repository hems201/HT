package com.example.ht.entries;

import java.util.ArrayList;
import java.util.Date;

public abstract class Entry {
    String entryID;
    Date date;
    Integer totalCO;


    public String getEntryID() { return entryID; }

    public Date getInfo() { return date; }

    public Integer getTotalCO() { return totalCO; }

    public abstract void countTotalCO();
    public abstract void readXML(String url);

}
