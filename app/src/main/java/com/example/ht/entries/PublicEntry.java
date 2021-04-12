package com.example.ht.entries;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class PublicEntry extends Entry{
    Double trainkm, buskm;
    String entryID;
    Date date;
    Double totalCO, busCO, trainCO;

    public PublicEntry(String x, Double a, Double b) {
        entryID = x;
        trainkm = a;
        buskm = b;

        date = Calendar.getInstance().getTime();
        countTotalCO(null);
    }

    @Override
    public void countTotalCO(ArrayList<Integer> travelValues) {
        //make request url from values
        // url example https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/TransportCalculator/PublicTransportEstimate?longDistanceBusYear=20&longDistanceTrainYear=20&metroweek=0&tramWeek=0&cityBusWeek=0&cityTrainWeek=0

        String url = "https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/TransportCalculator/PublicTransportEstimate?";

        //TODO open travelvalues and add to url

        url = url + "longDistanceBusYear=" + 20 + "&longDistanceTrainYear=" + 20 + "&metroweek=0&tramWeek=0&cityBusWeek=0&cityTrainWeek=0";

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
            totalCO = Double.parseDouble(doc.getDocumentElement().getElementsByTagName("Total").item(0).getTextContent());

            System.out.println(busCO + ", " + trainCO + ", " + totalCO);

        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

}
