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
import com.example.ht.User;
import com.example.ht.entries.EntryManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class publicEntryFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert getArguments() != null;
        User user = (User) getArguments().getSerializable("user");
        view =  inflater.inflate(R.layout.fragment_public_entry, container, false);

        //set up editTexts fields and button
        EditText editLongBus = view.findViewById(R.id.editLongBus);
        EditText editLongTrain = view.findViewById(R.id.editLongTrain);
        EditText editShortBus = view.findViewById(R.id.editShortBus);
        EditText editShortTrain = view.findViewById(R.id.editShortTrain);
        EditText editMetro = view.findViewById(R.id.editMetro);
        EditText editTram = view.findViewById(R.id.editTram);
        Button addPublicBtn = view.findViewById(R.id.addPublicEntry);

        addPublicBtn.setOnClickListener(v -> {
            //get text input contents
            int  lBus = Integer.parseInt(String.valueOf(editLongBus.getText()));
            int sBus = Integer.parseInt(String.valueOf(editShortBus.getText()));
            int lTrain = Integer.parseInt(String.valueOf(editLongTrain.getText()));
            int sTrain = Integer.parseInt(String.valueOf(editShortTrain.getText()));
            int tram = Integer.parseInt(String.valueOf(editTram.getText()));
            int metro = Integer.parseInt(String.valueOf(editMetro.getText()));

            //create a new entry and close fragment
            createPublicEntry(lBus,sBus,lTrain,sTrain,tram,metro, user);
        });


        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        System.out.println("public entry view created\n");

    }
    public void createPublicEntry(int lBus,int sBus, int lTrain,int sTrain,int tram,int metro, User user) {
        Intent intent = new Intent(getActivity().getBaseContext(), MenuActivity.class);

        // add relevant values to list
        ArrayList<Integer> travelValues = new ArrayList<Integer>();
        travelValues.add(lBus);
        travelValues.add(sBus);
        travelValues.add(lTrain);
        travelValues.add(sTrain);
        travelValues.add(tram);
        travelValues.add(metro);

        // get the user's entry manager
        EntryManager EM = user.getEM();
        SimpleDateFormat sdf =  new SimpleDateFormat("dd.MM.yyyy");
        EM.addEntry(3, travelValues, 0, sdf.format(Calendar.getInstance().getTime()), null);


        startActivity(intent);
    }
}
