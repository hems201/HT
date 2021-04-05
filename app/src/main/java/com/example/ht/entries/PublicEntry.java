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
        // url example https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/TransportCalculator/PublicTransportEstimate?longDistanceBusYear=20&longDistanceTrainYear=20&metroweek=0&tramWeek=0&cityBusWeek=0&cityTrainWeek=0

        String url = "https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/TransportCalculator/PublicTransportEstimate?";
        //open travelvalues and add to url

        // send request

        // response -> save totalCO
    }
}
