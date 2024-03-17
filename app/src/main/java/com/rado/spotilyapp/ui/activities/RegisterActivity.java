package com.rado.spotilyapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rado.spotilyapp.DatabaseHelper;
import com.rado.spotilyapp.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText fullNameEditText, phoneNumEditText, emailAddressEditText, genderEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        databaseHelper = new DatabaseHelper(this);

        //link
        fullNameEditText = findViewById(R.id.fullName);
        phoneNumEditText = findViewById(R.id.phoneNum);
        emailAddressEditText = findViewById(R.id.emailAddress);
        genderEditText = findViewById(R.id.gender);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirmPassword);
        registerButton = findViewById(R.id.button);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();

            }
        });

        //create function for register



    }
    private void register(){
        String fullName = fullNameEditText.getText().toString().trim();
        String phoneNum = phoneNumEditText.getText().toString().trim();
        String emailAddress = emailAddressEditText.getText().toString().trim();
        String gender = genderEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();



        // If all validations pass, insert data into SQLite database
        long id = databaseHelper.addUser(fullName, phoneNum, emailAddress, gender, password);
        if (id != -1) {
            // Registration successful
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            // Optionally, navigate to another activity
            // For example: startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        } else {
            // Registration failed
            Toast.makeText(this, "User with the email is already exist", Toast.LENGTH_SHORT).show();
        }
    }

}