package com.example.ht;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ht.fragments.addUserFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Define buttons and spinner from layout
        Button addUserBtn = findViewById(R.id.addUserBtn);
        Button logInBtn = findViewById(R.id.logInBtn);
        Spinner userSpinner = findViewById(R.id.userSpinner);

        //Initialize singleton UserManager
        UserManager UM = UserManager.getInstance();

        //Log in button set up
        View.OnClickListener lis = v -> {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            user = (User) userSpinner.getSelectedItem();
            System.out.println(user.getUserid());

            //send userinfo
            Bundle nbundle = new Bundle();
            nbundle.putSerializable("user", user);
            intent.putExtras(nbundle);
            startActivity(intent);
        };
        logInBtn.setOnClickListener(lis);

        //AddUserBtn set up
        View.OnClickListener listener = v -> {
            Fragment frag = new addUserFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragmentFrame, frag);
            transaction.commit();
        };
        addUserBtn.setOnClickListener(listener);

        //UserSpinner set up
        List<User> user_array = UM.getUser_array();
        ArrayAdapter<User> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, user_array);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(spinnerAdapter);
    }
}