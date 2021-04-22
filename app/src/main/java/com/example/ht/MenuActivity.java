package com.example.ht;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ht.fragments.carEntryFragment;
import com.example.ht.fragments.flightEntryFragment;
import com.example.ht.fragments.publicEntryFragment;
import com.example.ht.fragments.tripDataFragment;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //TripDataSpinner set up
        Spinner tripDataSpinner = findViewById(R.id.tripDataSpinner);
        List<String> trip_data_list = new  ArrayList<>();
        trip_data_list.add("Select");
        trip_data_list.add("Public Transport");
        trip_data_list.add("Car");
        trip_data_list.add("Flight");
        trip_data_list.add("All transportation methods");
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,trip_data_list);
        spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tripDataSpinner.setAdapter(spinnerAdapter2);

        tripDataSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = tripDataSpinner.getSelectedItemPosition();
                System.out.println("ITEM SELECTED");
                if (pos > 0) {
                    System.out.println("GRAPH FRAGMENT GETTING STARTED");
                    Fragment frag = new tripDataFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", user);
                    bundle.putInt("position",pos);
                    frag.setArguments(bundle);
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragmentFrameMenu, frag);
                    transaction.commit();
                    System.out.println("GRAPH FRAGMENT COMMITTED");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //AddTripSpinner set up
        Spinner addTripSpinner = findViewById(R.id. addTripSpinner);
        List<String> add_trip_list = new  ArrayList<>();
        add_trip_list.add("Select transportation method");
        add_trip_list.add("Public Transport");
        add_trip_list.add("Car");
        add_trip_list.add("Flight");
        ArrayAdapter<String> spinnerAdapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,add_trip_list);
        spinnerAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addTripSpinner.setAdapter(spinnerAdapter3);

        addTripSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int pos = addTripSpinner.getSelectedItemPosition();
                Fragment frag = null;

                if (pos >= 1) {
                    if (pos == 1) {
                        frag = new publicEntryFragment();
                        System.out.println("Opening public");
                    }
                    else if (pos == 2) {
                        frag = new carEntryFragment();
                        System.out.println("Opening car");
                    }
                    else if (pos == 3) {
                        frag = new flightEntryFragment();
                        System.out.println("Opening flight");
                    }

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", user);
                    assert frag != null;
                    frag.setArguments(bundle);

                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragmentFrameMenu, frag);
                    transaction.commit();

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        user = (User) getIntent().getExtras().getSerializable("user");
        System.out.println("user mainactivitysta menuun;" + user.getUserid());

    }
}