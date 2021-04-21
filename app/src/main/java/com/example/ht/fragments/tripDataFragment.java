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
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class tripDataFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        User user = (User) getArguments().getSerializable("user");
        view =  inflater.inflate(R.layout.fragment_trip_data, container, false);

        makeGraph(view);

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        System.out.println("trip data view created\n");

    }

    public void makeGraph(View view) { //..,dataArray

        GraphView graph = view.findViewById(R.id.line_graph);

        //BAR GRAPH?

        // MALLI LISTAA VARTEN:
//        DataPoint[] dp = new DataPoint[10];
//        for(int i=0;i<=array.size();i++){
//            x = datearray[i];
//            y = COarray[i];
//         dp[i] = new DataPoint(x, y);
//        }
//
//        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);


        //SIMPPELI MALLI:
//        LineGraphSeries<DataPoint> lineSeries = new LineGraphSeries<>(new DataPoint[] {
//                new DataPoint(0, 1),
//                new DataPoint(1, 5),
//                new DataPoint(2, 3),
//                new DataPoint(3, 2),
//                new DataPoint(4, 6)
//        });
        //graph.addSeries(lineSeries);
    }
}
