package com.example.ht.entries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PublicEntry extends Entry{
    Integer trainkm, buskm;
    String entryID;
    Date date;
    Integer totalCO;

    public PublicEntry(String x, Integer a, Integer b) {
        entryID = x;
        trainkm = a;
        buskm = b;

        date = Calendar.getInstance().getTime();
    }

    @Override
    public void countTotalCO(ArrayList<Integer> travelValues) {
        //send request to ilmastodieetti to calculate CO2
    }
}
