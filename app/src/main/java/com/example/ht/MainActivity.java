package com.example.ht;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.ht.fragments.addUserFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize singleton UserManager
        UserManager UM = UserManager.getInstance();


        //AddUserBtn set up
        Button addUserBtn = findViewById(R.id.addUserBtn);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frag = new addUserFragment();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragmentFrame, frag);
                transaction.commit();
            }
        };
        addUserBtn.setOnClickListener(listener);

        //UserSpinner set up
        Spinner userSpinner = findViewById(R.id.userSpinner);
        List<User> user_array = UM.getUser_array();
        ArrayAdapter<User> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, user_array);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(spinnerAdapter);

        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) userSpinner.getSelectedItem();
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                intent.putExtra("username", user);
                System.out.println("User sent from main activity to menu acitivity");
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}