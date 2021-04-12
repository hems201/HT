package com.example.ht.entries;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class PublicEntry extends Entry{
    int sTrainkm, sBuskm, lTrainkm, lBuskm, metrokm, tramkm, entryID;
    Date date;
    Integer totalCO, busCO, trainCO;

    public PublicEntry(int id, int st, int sb, int lt, int lb, int m, int t) {
        entryID = id;
        tramkm = t;
        lBuskm = lb;
        sBuskm = sb;
        sTrainkm = st;
        lTrainkm = lt;
        metrokm = m;

        date = Calendar.getInstance().getTime();
    }

    @Override
    public void countTotalCO(ArrayList<Integer> travelValues) {
        //send request to ilmastodieetti to calculate CO2
        // url example https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/TransportCalculator/PublicTransportEstimate?longDistanceBusYear=20&longDistanceTrainYear=20&metroweek=0&tramWeek=0&cityBusWeek=0&cityTrainWeek=0

        String url = "https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/TransportCalculator/PublicTransportEstimate?";

        //TODO open travelvalues and add to url

        url = url + "longDistanceBusYear=" + 20 + "&longDistanceTrainYear=" + 20 + "&metroweek=0&tramWeek=0&cityBusWeek=0&cityTrainWeek=0";

        // send request
        readXML(url);
    }

    @Override
    public void readXML(String url) {
        //parse the emission values from the response document
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            // get response document
            Document doc = builder.parse(url);

            System.out.println(doc.getDocumentElement().getNodeName());

            busCO = Integer.parseInt(doc.getDocumentElement().getElementsByTagName("Bus").item(0).getTextContent());
            trainCO = Integer.parseInt(doc.getDocumentElement().getElementsByTagName("Train").item(0).getTextContent());
            totalCO = Integer.parseInt(doc.getDocumentElement().getElementsByTagName("Total").item(0).getTextContent());

            System.out.println(busCO + ", " + trainCO + ", " + totalCO);

        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

}
