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
import com.example.ht.R;
import com.example.ht.UserManager;

public class addUserFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_user, container, false);

        //CreateUserBtn set up
        Button createUserBtn = view.findViewById(R.id.createUserBtn);
        EditText editTextName = this.view.findViewById(R.id.editTextName);

        createUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                createUser(name);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        System.out.println("user fragment view created\n");
    }

    //Sends the userinfo to the MainActivity
    public void createUser(String name) {
        Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
        UserManager UM = UserManager.getInstance();
        UM.addUser(name);
        startActivity(intent);
    }
}
