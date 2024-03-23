package com.rado.spotilyapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rado.spotilyapp.R;
import com.rado.spotilyapp.DatabaseHelper;

public class Admin_Confirm extends AppCompatActivity {

    private EditText productTypeEditTxt;
    private EditText productNameEditTxt;
    private EditText weightEditTxt;
    private EditText priceEditTxt;
    private EditText descriptionEditTxt;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_confirm);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Linking XML elements with Java
        productTypeEditTxt = findViewById(R.id.productTypeEditTxt);
        productNameEditTxt = findViewById(R.id.productNameEditTxt);
        weightEditTxt = findViewById(R.id.weightEditTxt);
        priceEditTxt = findViewById(R.id.priceEditText);
        descriptionEditTxt = findViewById(R.id.descItem);
    }

    public void backTo(View view) {
        onBackPressed();
    }

    public void addProductHandler(View view) {
        // Retrieve data from EditText fields
        String productType = productTypeEditTxt.getText().toString().trim();
        String productName = productNameEditTxt.getText().toString().trim();
        String weight = weightEditTxt.getText().toString().trim();
        String price = priceEditTxt.getText().toString().trim();
        String description = descriptionEditTxt.getText().toString().trim();

        // Validate input fields (optional)

        // Insert data into the "products" table
        long result = databaseHelper.insertProduct(productType, productName, weight, price, description);

        if (result != -1) {
            // Data inserted successfully
            Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();
            // Optionally, clear the form fields    
            clearFormFields();
        } else {
            // Failed to insert data
            Toast.makeText(this, "Failed to add product", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFormFields() {
        productTypeEditTxt.setText("");
        productNameEditTxt.setText("");
        weightEditTxt.setText("");
        priceEditTxt.setText("");
        descriptionEditTxt.setText("");
    }
    }
