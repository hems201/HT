package com.example.ht.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ht.ContextProvider;
import com.example.ht.MenuActivity;
import com.example.ht.R;
import com.example.ht.User;
import com.example.ht.entries.CarEntry;
import com.example.ht.entries.EntryManager;
import com.example.ht.entries.FlightEntry;
import com.example.ht.entries.PublicEntry;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class tripDataFragment extends Fragment {
    View view;
    User user;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_trip_data, container, false);

        // Get current user and tripDataSpinner position from MenuActivity
        assert getArguments() != null;
        user = (User) getArguments().getSerializable("user");
        int pos = getArguments().getInt("position");    //Spinner position for making the right graph

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

        EntryManager EM = user.getEM();

        // Create data points (dp) for the graph from the correct entry array
        DataPoint[] dp;
        if (pos == 1) {
            ArrayList<PublicEntry> array = EM.getPublicArray();
            if(array.size()>0) {
                dp = getPublicData(array);
            } else {
                dp = null;
                closeFragment();
                Toast.makeText(ContextProvider.getContext(),"Add a public transport entry first.", Toast.LENGTH_SHORT).show();
            }

        } else if (pos == 2) {
            ArrayList<CarEntry> array = EM.getCarEntryArray();
            if(array.size()>0) {
                dp = getCarData(array);
            } else {
                dp = null;
                closeFragment();
                Toast.makeText(ContextProvider.getContext(),"Add a car entry first.", Toast.LENGTH_SHORT).show();
            }

        } else if (pos ==3) {
            ArrayList<FlightEntry> array = EM.getFlightEntryArray();
            if(array.size()>0) {
                dp = getFlightData(array);
            } else {
                dp = null;
                closeFragment();
                Toast.makeText(ContextProvider.getContext(),"Add a flight entry first.", Toast.LENGTH_SHORT).show();
            }
        } else { dp=null;}

        //Plot if data points exist
        if (dp != null) {
            // define graph from layout
            GraphView graph = view.findViewById(R.id.line_graph);
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
            double maxX = dp[dpSize-1].getX();
            double minX = dp[0].getX();
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

    // Go back to MenuActivity if entry arrays are empty
    public void closeFragment(){
        Intent intent = new Intent(getActivity().getBaseContext(), MenuActivity.class);
        // send user data
        Bundle nbundle = new Bundle();
        nbundle.putSerializable("user", user);
        intent.putExtras(nbundle);
        startActivity(intent);
    }
}
