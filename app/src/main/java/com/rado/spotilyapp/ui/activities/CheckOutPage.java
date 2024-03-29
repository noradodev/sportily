package com.rado.spotilyapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rado.spotilyapp.CheckoutAdapter;
import com.rado.spotilyapp.DatabaseHelper;
import com.rado.spotilyapp.Product;
import com.rado.spotilyapp.R;

import java.util.ArrayList;
import java.util.List;


public class CheckOutPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CheckoutAdapter checkoutAdapter;
    private List<Product> cartItemList;
    private DatabaseHelper databaseHelper;
    private TextView totalPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        recyclerView = findViewById(R.id.listItem);
        totalPriceTextView = findViewById(R.id.totalTxt);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartItemList = new ArrayList<>();
        checkoutAdapter = new CheckoutAdapter(cartItemList);
        recyclerView.setAdapter(checkoutAdapter);

        databaseHelper = new DatabaseHelper(this);

        // Load cart items from the database
        loadCartItems();
    }

    private void loadCartItems() {
        cartItemList.clear();
        cartItemList.addAll(databaseHelper.getAllCartProducts());
        checkoutAdapter.notifyDataSetChanged();
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double totalPrice = calculateTotalPrice();
        totalPriceTextView.setText("$" + totalPrice);
    }

    private double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (Product product : cartItemList) {
            double price = Double.parseDouble(product.getPrice());
            totalPrice += price * product.getQuantity();
        }
        return totalPrice;
    }

    public void backPressedBtn(View view) {
        onBackPressed();
    }
}