package com.rado.spotilyapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rado.spotilyapp.R;
import com.rado.spotilyapp.SessionManager;

public class Action extends AppCompatActivity {
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        sessionManager = new SessionManager(this);
    }

    public void addProduct(View view) {
        Intent addPro = new Intent(Action.this, Admin_Confirm.class);
        startActivity(addPro);
    }

    public void logOut(View view) {
        sessionManager.logout();
        // Redirect to the login page or perform any other necessary actions
        // For example: ;
        startActivity(new Intent(this, LoginActivity.class));
        finish(); // Finish the current activity to prevent the user from going back
    }
}