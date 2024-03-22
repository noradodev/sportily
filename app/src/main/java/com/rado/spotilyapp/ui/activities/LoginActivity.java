package com.rado.spotilyapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rado.spotilyapp.DatabaseHelper;
import com.rado.spotilyapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(this);

        // Linking XML elements with Java
        emailEditText = findViewById(R.id.emailAddress);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.button);

        // Setting click listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform login logic here
                loginUser();
            }
        });
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String user = "user";
        String admin ="admin";
        // Perform validation checks...

        // Authenticate user login
        boolean isValidUser = databaseHelper.authenticateUser(email, password, user);
        boolean isValidAdmin = databaseHelper.authenticateUser(email, password, admin);
        if (isValidUser) {
            // Login successful
            Toast.makeText(this, "Login successful as Users", Toast.LENGTH_SHORT).show();
            // Optionally, navigate to another activity
            // For example: startActivity(new Intent(LoginActivity.this, DashboardActivity.class));

            Intent toUser = new Intent(LoginActivity.this, HomepageActivity.class);
            startActivity(toUser);
        } else {

            if (isValidAdmin) {
                // Login successful
                Toast.makeText(this, "Login successful as Admin", Toast.LENGTH_SHORT).show();
                // Optionally, navigate to another activity
                // For example: startActivity(new Intent(LoginActivity.this, DashboardActivity.class));

                Intent toAdmin = new Intent(LoginActivity.this, Admin_add.class);
                startActivity(toAdmin);
            } else {

                // Login failed
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();

            }
        }
    }
}