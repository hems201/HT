package com.example.ht.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ht.MenuActivity;
import com.example.ht.R;
import com.example.ht.entries.EntryManager;

import java.util.ArrayList;

public class carEntryFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_car_entry, container, false);

        //set up editTexts fields and button
        EditText editCarYear = view.findViewById(R.id.editCarYear);
        EditText editDriveDist= view.findViewById(R.id.editDriveDist);
        EditText editPassengers = view.findViewById(R.id.editPassengers);

        Button addCarBtn = view.findViewById(R.id.addCarEntry);
        addCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get text input contents
                Integer km = Integer.parseInt(String.valueOf(editDriveDist.getText()));
                Integer carYear = Integer.parseInt(String.valueOf(editCarYear.getText()));
                Integer passengers = Integer.parseInt(String.valueOf(editPassengers.getText()));

                //create a new entry and close fragment
                createCarEntry(km, carYear, passengers);
            }
        });


        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        System.out.println("car entry view created\n");

    }
    public void createCarEntry(Integer km, Integer carYear, Integer passengers) {
        Intent intent = new Intent(getActivity().getBaseContext(), MenuActivity.class);
        EntryManager EM = EntryManager.getInstance();

        // add relevant values to list
        ArrayList<Integer> travelValues = new ArrayList<Integer>();
        travelValues.add(km);
        travelValues.add(carYear);
        travelValues.add(passengers);

        EM.addEntry(1, travelValues);
        startActivity(intent);
    }
}
