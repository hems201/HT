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

public class publicEntryFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_public_entry, container, false);

        //set up editTexts fields and button
        EditText editLongBus = view.findViewById(R.id.editLongBus);
        EditText editLongTrain = view.findViewById(R.id.editLongTrain);
        EditText editShortBus = view.findViewById(R.id.editShortBus);
        EditText editShortTrain = view.findViewById(R.id.editShortTrain);
        EditText editMetro = view.findViewById(R.id.editMetro);
        EditText editTram = view.findViewById(R.id.editTram);
        Button addPublicBtn = view.findViewById(R.id.addPublicEntry);

        addPublicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get text input contents
                int  lBus = Integer.parseInt(String.valueOf(editLongBus.getText()));
                int sBus = Integer.parseInt(String.valueOf(editShortBus.getText()));
                int lTrain = Integer.parseInt(String.valueOf(editLongTrain.getText()));
                int sTrain = Integer.parseInt(String.valueOf(editShortTrain.getText()));
                int tram = Integer.parseInt(String.valueOf(editTram.getText()));
                int metro = Integer.parseInt(String.valueOf(editMetro.getText()));

                //create a new entry and close fragment
                createPublicEntry(lBus,sBus,lTrain,sTrain,tram,metro);
            }
        });


        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        System.out.println("public entry view created\n");

    }
    public void createPublicEntry(int lBus,int sBus, int lTrain,int sTrain,int tram,int metro) {
        Intent intent = new Intent(getActivity().getBaseContext(), MenuActivity.class);
        EntryManager EM = EntryManager.getInstance();
        //EM.addEntry();
        startActivity(intent);
    }
}
