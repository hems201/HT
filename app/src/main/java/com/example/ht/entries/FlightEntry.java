package com.example.ht.entries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FlightEntry extends Entry{
    Integer planeFin, planeEu, planeCa, planeTra;
    String entryID;
    Date date;
    Integer totalCO;

    public FlightEntry(String x,Integer a, Integer b, Integer c, Integer d) {
        entryID = x;
        planeFin = a;
        planeEu = b;
        planeCa = c;
        planeTra = d;

        date = Calendar.getInstance().getTime();
    }

    @Override
    public void countTotalCO(ArrayList<Integer> travelValues) {
        //send request to ilmastodieetti to calculate CO2
    }
}
