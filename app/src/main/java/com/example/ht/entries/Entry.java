package com.example.ht.entries;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public abstract class Entry implements Serializable {
    String entryID;
    Date date;
    Double totalCO;


    public String getEntryID() { return entryID; }

    public Date getDate() { return date; }

    public Double getTotalCO() { return totalCO; }

    public abstract void countTotalCO();
    public abstract void readXML(String url);

}
