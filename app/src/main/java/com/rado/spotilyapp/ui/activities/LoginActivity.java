package com.rado.spotilyapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rado.spotilyapp.DatabaseHelper;
import com.rado.spotilyapp.SessionManager;
import com.rado.spotilyapp.R;


public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;

    private TextView signupTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        // Linking XML elements with Java
        emailEditText = findViewById(R.id.emailAddress);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.button);
        signupTextView = findViewById(R.id.textSignup);

        // Setting click listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform login logic here
                loginUser();
            }
        });


        //momo
        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Navigate to Register Activity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Check if user is already logged in
        if (sessionManager.isLoggedIn()) {
            // User is already logged in, navigate to appropriate page
            if (sessionManager.getUserRole().equals("user")) {
                navigateToHomepage();
            } else if (sessionManager.getUserRole().equals("admin")) {
                navigateToAdminPage();
            }
        }
    }




    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String user = "user";
        String admin = "admin";

        // Perform validation checks...

        // Authenticate user login
        boolean isValidUser = databaseHelper.authenticateUser(email, password, user);
        boolean isValidAdmin = databaseHelper.authenticateUser(email, password, admin);

        if (isValidUser) {
            // Login successful
            Toast.makeText(this, "Login successful as User", Toast.LENGTH_SHORT).show();
            sessionManager.setLogin(true, user); // Set login status to true and user role

            navigateToHomepage();
        } else if (isValidAdmin) {
            // Login successful as admin
            Toast.makeText(this, "Login successful as Admin", Toast.LENGTH_SHORT).show();
            sessionManager.setLogin(true, admin); // Set login status to true and user role
            navigateToAdminPage();
        } else {
            // Login failed
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }


    private void navigateToHomepage() {
        Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
        startActivity(intent);
        finish(); // Finish LoginActivity so user cannot go back to it after logging in
    }

    private void navigateToAdminPage() {
        Intent intent = new Intent(LoginActivity.this,  Action.class);
        startActivity(intent);
        finish(); // Finish LoginActivity so user cannot go back to it after logging in
    }
}