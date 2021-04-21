package com.example.ht.entries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public abstract class Entry {
    Integer entryID;
    Date date;
    Double totalCO;


    public Integer getEntryID() { return entryID; }

    public String getDate() {
        SimpleDateFormat sdf =  new SimpleDateFormat("dd.MM.yyyy");
        String x = sdf.format(date);
        return x; }

    public Double getTotalCO() { return totalCO; }

    public abstract void countTotalCO();
    public abstract void readXML(String url);

}
