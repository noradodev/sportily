package com.rado.spotilyapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rado.spotilyapp.DatabaseHelper;
import com.rado.spotilyapp.Product;
import com.rado.spotilyapp.R;
public class ProductDetail extends AppCompatActivity {

    private TextView itemNameTextView, itemPriceTextView, itemTypeTextView, descListTextView1, descListTextView2, descListTextView3;
    private Button addToCartBtn;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Initialize views
        itemNameTextView = findViewById(R.id.itemNameTextView);
        itemTypeTextView = findViewById(R.id.itemtypeTextView);
        descListTextView1 = findViewById(R.id.descListTextView1);
        descListTextView2 = findViewById(R.id.descListTextView2);
        descListTextView3 = findViewById(R.id.descListTextView3);
        addToCartBtn = findViewById(R.id.addToCartBtn);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Get the product ID from the intent
        int productId = getIntent().getIntExtra("productId", -1);

        // Use the product ID to fetch the product details from the database and display them
        Product product = databaseHelper.getProductById(productId);

        if (product != null) {
            // Display product details
            itemNameTextView.setText(product.getProductName());
//            itemPriceTextView.setText(product.getPrice());
            itemTypeTextView.setText(product.getProductType());
            descListTextView1.setText(product.getDescription());
//            descListTextView2.setText("+ Enhance your home gym setup with our space-saving dumbbells");
//            descListTextView3.setText("Build strength and endurance with our ergonomic dumbbell designs.");
        }

        // Set onClickListener for the addToCartBtn if needed
    }


    public void DetailBack(View view) {
        onBackPressed();
    }
}
