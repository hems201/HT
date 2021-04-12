package com.example.ht.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.ht.R;

public class tripDataFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_trip_data, container, false);

        //makeGraph(view);

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        System.out.println("trip data view created\n");

    }

//    public void makeGraph(View view) {
//        // init example series data
//        GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
//                new GraphViewData(1, 2.0d)
//                , new GraphViewData(2, 1.5d)
//                , new GraphViewData(3, 2.5d)
//                , new GraphViewData(4, 1.0d)
//        });
//
//        LineGraphView graphView = new LineGraphView(
//                getActivity() // context
//                , "GraphViewDemo" // heading
//        );
//        graphView.addSeries(exampleSeries); // data
//
//        try {
//            LinearLayout layout = (LinearLayout) view.findViewById(R.id.graph);
//            layout.addView(graphView);
//        } catch (NullPointerException e) {
//            // something to handle the NPE.
//        }
//    }
}
