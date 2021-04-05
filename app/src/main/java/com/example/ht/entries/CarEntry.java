package com.example.ht.entries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CarEntry extends Entry {
    Integer km;
    String entryID;
    Date date;
    Integer totalCO;

    public CarEntry(String x, Integer a) {
        entryID = x;
        km = a;

        date = Calendar.getInstance().getTime();
    }

    @Override
    public void countTotalCO(ArrayList<Integer> travelValues) {
        //send request to ilmastodieetti to calculate CO2
    }

}
