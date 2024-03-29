package com.rado.spotilyapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rado.spotilyapp.DatabaseHelper;
import com.rado.spotilyapp.R;
import com.rado.spotilyapp.SessionManager;
import com.rado.spotilyapp.User;
import com.rado.spotilyapp.ui.activities.LoginActivity;
import com.rado.spotilyapp.ui.activities.View_history;

public class SettingFragment extends Fragment {

    private SessionManager sessionManager;
    private DatabaseHelper databaseHelper;
    private TextView nameTxt, phoneTxt, emailTxt, genderTxt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        sessionManager = new SessionManager(requireContext());
        databaseHelper = new DatabaseHelper(requireContext());

        // Initialize views
        nameTxt = view.findViewById(R.id.nameEditTxt);
        phoneTxt = view.findViewById(R.id.phoneEditTxt);
        emailTxt = view.findViewById(R.id.emailEditTxt);
        genderTxt = view.findViewById(R.id.genderEditTxt);

        String email = "rado@gmail.com";

        // Retrieve all user details from the database based on the email

        User user = databaseHelper.getUserByEmail(email);

        // Set the retrieved user details to the TextViews
        // Set the retrieved user details to the TextViews
        if (user != null) {
            nameTxt.setText(user.getName());
            phoneTxt.setText(user.getPhone());
            emailTxt.setText(user.getEmail());
            genderTxt.setText(user.getGender());
        } else {
            // Handle the case where user is null
            // For example, you could show a message or prompt the user to log in again
        }

        Button viewHistoryButton = view.findViewById(R.id.bViewHistory);
//        viewHistoryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SettingFragment.this, View_history.class);
//                startActivity(intent);
//            }
//        });
        viewHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), View_history.class);
                startActivity(intent);
            }
        });


        Button logoutButton = view.findViewById(R.id.bLogout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        return view;
    }

    public void logout() {
        sessionManager.logout();
        // Redirect to the login page or perform any other necessary actions
        // For example:
        startActivity(new Intent(requireContext(), LoginActivity.class));
        requireActivity().finish(); // Finish the current activity to prevent the user from going back
    }
}
