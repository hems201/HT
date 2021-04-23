package com.example.ht.entries;

import org.w3c.dom.Document;
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

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class CarEntry extends Entry implements Serializable{
    Integer km, carYear, passengers;

    public CarEntry(ArrayList<Integer> travelValues, Integer id, String oldDate, Double co) {
        //open travelValues

        entryID = id;
        km = travelValues.get(0);
        carYear = travelValues.get(1);
        passengers = travelValues.get(2);

        try {
            date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);

        // see if CO are already included
        if (co!=null) {
            totalCO = co;
        } else {
            countTotalCO();
        }
    }

    @Override
    public void countTotalCO() {
        // make request url
        String url = "https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/TransportCalculator/CarEstimate?"
                + "query.buildYear=" + carYear
                + "&query.driveDistance=" + km
                + "&query.size=mini"
                + "&query.passengerCount=" + passengers;
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

                totalCO = Double.parseDouble(doc.getDocumentElement().getTextContent());
            } else {
                //TODO toast unsuccessful request
                totalCO = 0.0;
            }
            System.out.println("totalCO: " + totalCO);

        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public Integer getKm(){return km;}
    public Integer getCarYear(){return carYear;}
    public Integer getPassengers(){return passengers;}
}
