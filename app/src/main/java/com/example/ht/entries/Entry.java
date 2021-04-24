package com.example.ht.entries;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.io.Serializable;
import java.util.Date;

public abstract class Entry implements Serializable {
    Integer entryID;
    Date date;
    Double totalCO;

    public Integer getEntryID() { return entryID; }

    public String getDate() {
        SimpleDateFormat sdf =  new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return sdf.format(date);
    }

    public Date getDateTime() {return date;}

    public Double getTotalCO() { return totalCO; }

    public abstract void countTotalCO(HttpURLConnection con);
    public abstract void readXML();
}
