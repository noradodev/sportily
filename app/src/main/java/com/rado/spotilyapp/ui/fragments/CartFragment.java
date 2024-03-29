package com.rado.spotilyapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rado.spotilyapp.DatabaseHelper;
import com.rado.spotilyapp.Product;
import com.rado.spotilyapp.R;
import com.rado.spotilyapp.ui.activities.CheckOutPage;

import java.util.ArrayList;
import java.util.List;



public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<Product> cartItemList;
    private DatabaseHelper databaseHelper;
    private TextView totalPriceTextView;
    private TextView emptyCartTextView;
    private TextView checkoutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_cart);
        totalPriceTextView = view.findViewById(R.id.text_view_total_price);
        emptyCartTextView = view.findViewById(R.id.text_view_empty_cart); // Add this line
        checkoutButton = view.findViewById(R.id.button_checkout); // Add this line

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartItemList = new ArrayList<>();
        cartAdapter = new CartAdapter(getContext(), cartItemList, totalPriceTextView); // Pass totalPriceTextView to the adapter
        recyclerView.setAdapter(cartAdapter);

        databaseHelper = new DatabaseHelper(getContext());

        // Load cart items from the database
        loadCartItems();

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cartItemList.isEmpty()) {
                    // Proceed to checkout
                    navigateToCheckoutPage();
                } else {
                    // Show a message if the cart is empty
                    Toast.makeText(getContext(), "Your cart is empty.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void loadCartItems() {
        List<Product> cartProducts = databaseHelper.getAllCartProducts();
        cartItemList.clear();
        cartItemList.addAll(cartProducts);
        cartAdapter.notifyDataSetChanged();
        updateTotalPrice();
        updateEmptyCartView(); // Update empty cart TextView
    }

    private void updateTotalPrice() {
        double totalPrice = calculateTotalPrice();
        totalPriceTextView.setText("Total Price: $" + totalPrice);
    }

    private double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (Product product : cartItemList) {
            double price = Double.parseDouble(product.getPrice());
            totalPrice += price * product.getQuantity();
        }
        return totalPrice;
    }

    private void updateEmptyCartView() {
        if (cartItemList.isEmpty()) {
            emptyCartTextView.setVisibility(View.VISIBLE);
            checkoutButton.setEnabled(false);
        } else {
            emptyCartTextView.setVisibility(View.GONE);
            checkoutButton.setEnabled(true);
        }
    }

    private void navigateToCheckoutPage() {
        // Implement navigation to checkout page here
        // For example:
         Intent intent = new Intent(getContext(), CheckOutPage.class);
         startActivity(intent);
    }
}