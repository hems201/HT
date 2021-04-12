package com.example.ht.entries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CarEntry extends Entry {
    Integer km;
    Integer entryID;
    Integer passengers;
    Integer carYear;
    Date date;
    Integer totalCO;

    public CarEntry(int id, int k, int p, int y) {
        entryID = id;
        km = k;
        passengers = p;
        carYear = y;

        date = Calendar.getInstance().getTime();
    }

    @Override
    public void countTotalCO(ArrayList<Integer> travelValues) {
        //send request to ilmastodieetti to calculate CO2
        //"https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/TransportCalculator/CarEstimate?query.buildYear=2010&query.driveDistance=10&query.size=mini&query.passengerCount=2"
    }

    @Override
    public void readXML(String url) {

    }

}
