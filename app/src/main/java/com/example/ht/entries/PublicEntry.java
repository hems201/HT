package com.example.ht.entries;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class PublicEntry extends Entry implements Serializable {
    Integer lBus, sBus, lTrain,sTrain, tram, metro;
    Double busCO, trainCO, otherCO;


    public PublicEntry(ArrayList<Integer> travelValues, Integer id, String oldDate, ArrayList<Double> coList) {
        // open travelValues
        entryID = id;
        lBus = travelValues.get(0); sBus = travelValues.get(1);
        lTrain = travelValues.get(2); sTrain = travelValues.get(3);
        tram = travelValues.get(4); metro = travelValues.get(5);

        try {
            date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // see if CO are already included
        if (coList!=null) {
            totalCO = coList.get(0); busCO = coList.get(1);
            trainCO = coList.get(2); otherCO = coList.get(3);
        } else {
            countTotalCO();
        }
    }
    public Integer getlBus(){return lBus;}
    public Integer getsBus(){return sBus;}
    public Integer getlTrain(){return lTrain;}
    public Integer getsTrain(){return sTrain;}
    public Integer getTram(){return tram;}
    public Integer getMetro(){return metro;}
    public Double getBusCO(){return busCO;}
    public Double getOtherCO() {return otherCO;}
    public Double getTrainCO() {return trainCO;}

    @Override
    public void countTotalCO() {
        //make request url from values
        String url = "https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/TransportCalculator/PublicTransportEstimate?"
                + "longDistanceBusYear=" +  lBus
                + "&longDistanceTrainYear=" + lTrain
                + "&metroweek=" +  metro
                + "&tramWeek=" + tram
                + "&cityBusWeek=" + sBus
                + "&cityTrainWeek=" + sTrain;
        System.out.println(url);
        // send request
        readXML(url);
    }

    @Override
    public void readXML(String url) {
        //send request to ilmastodieetti to calculate CO2
        //parse the emission values from the response document
        try {
            // https://chillyfacts.com/java-send-url-http-request-read-xml-response/
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestProperty("content-type", "application/xml; charset=utf-8");

            int responseCode = con.getResponseCode();
            System.out.println(responseCode);

            // get TotalCO if request success
            if (responseCode==200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                con.disconnect();

                // fix encoding
                String responseString = response.toString().replaceAll("[^\\x20-\\x7e]", "");

                Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .parse(new InputSource(new StringReader(responseString)));

                System.out.println(doc.getDocumentElement().getNodeName());

                busCO = Double.parseDouble(doc.getDocumentElement().getElementsByTagName("Bus").item(0).getTextContent());
                trainCO = Double.parseDouble(doc.getDocumentElement().getElementsByTagName("Train").item(0).getTextContent());
                otherCO = Double.parseDouble(doc.getDocumentElement().getElementsByTagName("Other").item(0).getTextContent());
                totalCO = Double.parseDouble(doc.getDocumentElement().getElementsByTagName("Total").item(0).getTextContent());
            } else {
                //TODO toast unsuccessful request
                totalCO = 0.0;
                busCO = 0.0;
                trainCO = 0.0;
                otherCO = 0.0;
            }
            System.out.println(busCO + ", " + trainCO + ", " + otherCO + ", "+ totalCO);

        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
