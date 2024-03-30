package com.rado.spotilyapp.ui.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rado.spotilyapp.DatabaseHelper;
import com.rado.spotilyapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Admin_Confirm extends AppCompatActivity {

    private EditText productTypeEditTxt;
    private EditText productNameEditTxt;
    private EditText weightEditTxt;
    private EditText priceEditTxt;
    private EditText descriptionEditTxt;
    private DatabaseHelper databaseHelper;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri; // Store image URI instead of Bitmap
    private ImageView productImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_confirm);

        // Initialize views and database helper
        productTypeEditTxt = findViewById(R.id.productTypeEditTxt);
        productNameEditTxt = findViewById(R.id.productNameEditTxt);
        weightEditTxt = findViewById(R.id.weightEditTxt);
        priceEditTxt = findViewById(R.id.priceEditText);
        descriptionEditTxt = findViewById(R.id.descItem);
        productImageView = findViewById(R.id.productImageView);
        databaseHelper = new DatabaseHelper(this);
    }

    // Method to open image picker
    public void openFileChooser(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Method to handle the result of image picker
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData(); // Store the selected image URI
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                // Set the selected bitmap to productImageView
                productImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to save the selected image as a file
    private String saveImageToFile(Uri imageUri) {
        try {
            ContentResolver resolver = getContentResolver();
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, imageUri);

            // Define directory and file name
            String directory = getFilesDir() + "/Images/";
            String fileName = "image_" + System.currentTimeMillis() + ".jpg";

            // Create directory if it doesn't exist
            File directoryFile = new File(directory);
            if (!directoryFile.exists()) {
                directoryFile.mkdirs();
            }

            // Create file
            File file = new File(directory, fileName);

            // Save bitmap to file
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();

            // Return file path
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to add a product
    public void addProductHandler(View view) {
        // Retrieve data from EditText fields
        String productType = productTypeEditTxt.getText().toString().trim();
        String productName = productNameEditTxt.getText().toString().trim();
        String weight = weightEditTxt.getText().toString().trim();
        String price = priceEditTxt.getText().toString().trim();
        String description = descriptionEditTxt.getText().toString().trim();

        // Validate input fields (optional)

        // Save the selected image as a file
        String imagePath = saveImageToFile(imageUri);

        // Insert data into the "products" table
        long result = databaseHelper.insertProduct(productType, productName, weight, price, description, imagePath);

        if (result != -1) {
            // Data inserted successfully
            Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();
            // Optionally, clear the form fields and reset the image
            clearFormFields();
            productImageView.setImageResource(R.drawable.football); // Set the default image
        } else {
            // Failed to insert data
            Toast.makeText(this, "Failed to add product", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to clear form fields
    private void clearFormFields() {
        productTypeEditTxt.setText("");
        productNameEditTxt.setText("");
        weightEditTxt.setText("");
        priceEditTxt.setText("");
        descriptionEditTxt.setText("");
    }
}
