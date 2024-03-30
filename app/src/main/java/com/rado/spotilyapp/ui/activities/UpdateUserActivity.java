package com.rado.spotilyapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rado.spotilyapp.DatabaseHelper;
import com.rado.spotilyapp.R;
import com.rado.spotilyapp.User;

public class UpdateUserActivity extends AppCompatActivity {


    private EditText fullNameEditText;
    private EditText phoneNumberEditText;
    private EditText genderEditText;
    private Button updateUserButton;

    private DatabaseHelper dbHelper;
    private String userEmail; // Email of the user to be updated

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        // Initialize views
        fullNameEditText = findViewById(R.id.fullNameEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        genderEditText = findViewById(R.id.genderEditText);
        updateUserButton = findViewById(R.id.updateUserButton);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Get user email from Intent extras
        userEmail = getIntent().getStringExtra("user_email");

        // Fetch user details from database and populate EditText fields
        User user = dbHelper.getUserByEmail(userEmail);
        if (user != null) {
            fullNameEditText.setText(user.getName());
            phoneNumberEditText.setText(user.getPhone());
            genderEditText.setText(user.getGender());
        }

        // Handle click event for Update User button
        updateUserButton.setOnClickListener(view -> updateUser());
    }

    private void updateUser() {
        String fullName = fullNameEditText.getText().toString().trim();
        String phoneNumber = phoneNumberEditText.getText().toString().trim();
        String gender = genderEditText.getText().toString().trim();

        // Validate input fields
        if (TextUtils.isEmpty(fullName)) {
            fullNameEditText.setError("Please enter full name");
            return;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            phoneNumberEditText.setError("Please enter phone number");
            return;
        }

        if (TextUtils.isEmpty(gender)) {
            genderEditText.setError("Please enter gender");
            return;
        }

        // Update user details in the database
        boolean updated = dbHelper.updateUser(userEmail, fullName, phoneNumber, gender);
        if (updated) {
            Toast.makeText(this, "User details updated successfully", Toast.LENGTH_SHORT).show();

            finish();
        } else {
            Toast.makeText(this, "Failed to update user details", Toast.LENGTH_SHORT).show();
        }
    }
}