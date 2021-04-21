package com.example.ht.fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ht.R;
import com.example.ht.User;
import com.example.ht.entries.CarEntry;
import com.example.ht.entries.EntryManager;
import com.example.ht.entries.FlightEntry;
import com.example.ht.entries.PublicEntry;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class tripDataFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        User user = (User) getArguments().getSerializable("user");
        int pos = getArguments().getInt("position");    //Spinner position for making the right graph
        view =  inflater.inflate(R.layout.fragment_trip_data, container, false);

        if (pos == 1) {
            makePublicGraph(view,user);
        } else if (pos == 2) {
            makeCarGraph(view, user);
        } else if (pos == 3) {
            makeFlightGraph(view, user);
        } else if (pos == 4) {
            makeAllEntriesGraph(view,user);
        }

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        System.out.println("trip data view created\n");

    }

    public void makeCarGraph(View view, User user) {

        GraphView graph = view.findViewById(R.id.line_graph);

        EntryManager EM = user.getEM();
        ArrayList<CarEntry> array = EM.getCarEntryArray();
        int size = array.size();
        System.out.println(size);
        DataPoint[] dp = new DataPoint[size];
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.");  //TODO LAITA AIKA X-AKSELILLE
        for(int i=0;i<size;i++){
            Date x = array.get(i).getDate();
            Double y = array.get(i).getTotalCO();
            dp[i] = new DataPoint(x, y);
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
        graph.addSeries(series);
    }
    public void makePublicGraph(View view, User user) {

        GraphView graph = view.findViewById(R.id.line_graph);

        EntryManager EM = user.getEM();
        ArrayList<PublicEntry> array = EM.getPublicArray();
        int size = array.size();
        System.out.println(size);
        DataPoint[] dp = new DataPoint[size];
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM."); //TODO LAITA AIKA X-AKSELILLE
        for(int i=0;i<size;i++){
            Date x = array.get(i).getDate();
            Double y = array.get(i).getTotalCO();
            dp[i] = new DataPoint(x, y);
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
        graph.addSeries(series);
    }
    public void makeFlightGraph(View view, User user) {

        GraphView graph = view.findViewById(R.id.line_graph);

        EntryManager EM = user.getEM();
        ArrayList<FlightEntry> array = EM.getFlightEntryArray();
        int size = array.size();
        System.out.println(size);
        DataPoint[] dp = new DataPoint[size];
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM."); //TODO LAITA AIKA X-AKSELILLE
        for(int i=0;i<size;i++){
            Date x = array.get(i).getDate();
            Double y = array.get(i).getTotalCO();
            dp[i] = new DataPoint(x, y);
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
        graph.addSeries(series);
    }

    public void makeAllEntriesGraph(View view, User user) {

        GraphView graph = view.findViewById(R.id.line_graph);

        EntryManager EM = user.getEM();
        ArrayList<CarEntry> carArray = EM.getCarEntryArray();
        ArrayList<FlightEntry> flightArray = EM.getFlightEntryArray();
        ArrayList<PublicEntry> publicArray = EM.getPublicArray();
        double carSum = 0;
        for (int i = 0; i < carArray.size(); i++) {
            carSum = carSum + carArray.get(i).getTotalCO();
        }
        double publicSum = 0;
        for (int i = 0; i < publicArray.size(); i++) {
            publicSum = publicSum + publicArray.get(i).getTotalCO();
        }
        double flightSum = 0;
        for (int i = 0; i < flightArray.size(); i++) {
            flightSum = flightSum + flightArray.get(i).getTotalCO();
        }
        double sum = carSum + flightSum + publicSum;

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(0, sum),
                new DataPoint(1, carSum),
                new DataPoint(2, publicSum),
                new DataPoint(3, flightSum),
        });
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[]{"Total CO", "Car", "Public Transport", "Flights"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        series.setSpacing(10);
        graph.addSeries(series);
    }
}
