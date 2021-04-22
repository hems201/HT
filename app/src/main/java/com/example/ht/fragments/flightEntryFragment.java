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

import com.example.ht.MainActivity;
import com.example.ht.MenuActivity;
import com.example.ht.R;
import com.example.ht.User;
import com.example.ht.UserManager;
import com.example.ht.entries.EntryManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class flightEntryFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert getArguments() != null;
        User user = (User) getArguments().getSerializable("user");
        view =  inflater.inflate(R.layout.fragment_flight_entry, container, false);

        //set up editTexts fields and button
        EditText editFlightsFin = view.findViewById(R.id.editFlightsFin);
        EditText editFlightsEu = view.findViewById(R.id.editFlightsEu);
        EditText editFlightsCanary = view.findViewById(R.id.editFlightsCanary);
        EditText editFlightsCont = view.findViewById(R.id.editFlightsCont);

        Button addFlightBtn = view.findViewById(R.id.addFlightEntry);
        addFlightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get text input contents
                int planeFin = Integer.parseInt(String.valueOf(editFlightsFin.getText()));
                int planeEu = Integer.parseInt(String.valueOf(editFlightsEu.getText()));
                int planeCa = Integer.parseInt(String.valueOf(editFlightsCanary.getText()));
                int planeTra = Integer.parseInt(String.valueOf(editFlightsCont.getText()));

                //create a new entry and close fragment
                createFlightEntry(planeFin, planeEu, planeCa, planeTra, user);
            }
        });


        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        System.out.println("flight entry view created\n");

    }

    public void createFlightEntry(int fFin, int FEu, int FCan, int FTra, User user) {
        Intent intent = new Intent(getActivity().getBaseContext(), MenuActivity.class);

        // add relevant values to list
        ArrayList<Integer> travelValues = new ArrayList<Integer>();
        travelValues.add(fFin);
        travelValues.add(FEu);
        travelValues.add(FCan);
        travelValues.add(FTra);

        // get the user's entry manager
        EntryManager EM = user.getEM();
        SimpleDateFormat sdf =  new SimpleDateFormat("dd.MM.yyyy");
        EM.addEntry(2, travelValues, 0, sdf.format(Calendar.getInstance().getTime()), null,  user.getUserid());


        startActivity(intent);
    }

}
