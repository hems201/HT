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
import com.example.ht.entries.Entry;
import com.example.ht.entries.EntryManager;
import com.example.ht.entries.FlightEntry;
import com.example.ht.entries.PublicEntry;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class tripDataFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //TODO TUUNAA GRAAFEJA

        view =  inflater.inflate(R.layout.fragment_trip_data, container, false);

        // Get current user and tripDataSpinner position from MenuActivity
        User user = (User) getArguments().getSerializable("user");
        int pos = getArguments().getInt("position");    //Spinner position for making the right graph
        System.out.println("POSITION: " +  pos);

        //make the correct graph
        if (pos == 1 || pos == 2 || pos == 3){
            makeGraph(view, user, pos);
        } else {
            makeAllEntriesGraph(view,user);
        }
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        System.out.println("trip data view created\n");
    }

    public void makeGraph(View view, User user, int pos) {

        // define graph from layout
        GraphView graph = view.findViewById(R.id.line_graph);
        EntryManager EM = user.getEM();

        // Create data points (dp) for the graph from the correct entry array
        DataPoint[] dp;
        if (pos == 1) {
            ArrayList<PublicEntry> array = EM.getPublicArray();
            dp = getPublicData(array);
        } else if (pos == 2) {
            ArrayList<CarEntry> array = EM.getCarEntryArray();
            dp = getCarData(array);
        } else if (pos ==3) {
            ArrayList<FlightEntry> array = EM.getFlightEntryArray();
            dp = getFlightData(array);
        } else { dp=null;}

        //Format entry dates for X-axis
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if(isValueX){
                    return sdf.format(new Date((long)value));
                } else {
                    return super.formatLabel(value, isValueX);
                }
            }
        });


        // Set axises
        int dpSize = dp.length;
        Double maxX = dp[dpSize-1].getX();
        Double minX = dp[0].getX();
//        Double maxY = dp[dpSize-1].getY();
//        graph.getViewport().setMinY(0);
//        graph.getViewport().setMaxY(maxY);
//        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMaxX(maxX);
        graph.getViewport().setMinX(minX);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getGridLabelRenderer().setHumanRounding(false);
        graph.getViewport().setScrollable(true);

        // Plot line graph
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
        series.setDrawDataPoints(true);
        graph.addSeries(series);
    }

    //Methods for creating data points with Total CO and date for each entry type
    public DataPoint[] getCarData (ArrayList<CarEntry> array){
        int size = array.size();
        DataPoint[] dp = new DataPoint[size];
        for(int i=0;i<size;i++){
            Date x = array.get(i).getDateTime();
            Double y = array.get(i).getTotalCO();
            dp[i] = new DataPoint(x, y);
        }
        return dp;
    }
    public DataPoint[] getPublicData (ArrayList<PublicEntry> array){
        int size = array.size();
        DataPoint[] dp = new DataPoint[size];
        for(int i=0;i<size;i++){
            Date x = array.get(i).getDateTime();
            Double y = array.get(i).getTotalCO();
            dp[i] = new DataPoint(x, y);
        }
        return dp;
    }
    public DataPoint[] getFlightData (ArrayList<FlightEntry> array){
        int size = array.size();
        DataPoint[] dp = new DataPoint[size];
        for(int i=0;i<size;i++){
            Date x = array.get(i).getDateTime();
            Double y = array.get(i).getTotalCO();
            dp[i] = new DataPoint(x, y);
        }
        return dp;
    }

    // Created a bar graph with Total CO from all entry types and their sum
    public void makeAllEntriesGraph(View view, User user) {

        GraphView graph = view.findViewById(R.id.line_graph);

        // Get arrays and calculate CO sums
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

        // Format X-axis label
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[]{"Total CO", "Car", "Public Transport", "Flights"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        series.setSpacing(40);
        graph.addSeries(series);
    }
}
