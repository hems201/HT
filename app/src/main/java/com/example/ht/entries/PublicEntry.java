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
    Integer trainkm, buskm;
    String entryID;
    Date date;
    Integer totalCO, busCO, trainCO;

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

        //TODO open travelvalues and add to url

        url = url + "longDistanceBusYear=" + 20 + "&longDistanceTrainYear=" + 20 + "&metroweek=0&tramWeek=0&cityBusWeek=0&cityTrainWeek=0";

        // send request
        readXML(url);
    }

    public void readXML(String url) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            // get response document
            Document doc1 = builder.parse(url);

            System.out.println(doc1.getDocumentElement().getNodeName());

            busCO = Integer.parseInt(doc1.getDocumentElement().getAttributeNode("Bus").getValue());
            trainCO = Integer.parseInt(doc1.getDocumentElement().getAttributeNode("Train").getValue());
            totalCO = Integer.parseInt(doc1.getDocumentElement().getAttributeNode("Total").getValue());

            System.out.println(busCO + ", " + trainCO + ", " + totalCO);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

}
