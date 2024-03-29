package com.rado.spotilyapp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rado.spotilyapp.Product;
import com.rado.spotilyapp.R;

import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Product> cartItemList;
    private Context context;
    private TextView totalPriceTextView;

    public CartAdapter(Context context, List<Product> cartItemList, TextView totalPriceTextView) {
        this.context = context;
        this.cartItemList = cartItemList;
        this.totalPriceTextView = totalPriceTextView;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartItemList.get(position);
        holder.productNameTextView.setText(product.getProductName());
        holder.productPriceTextView.setText(product.getPrice() + " $");
        holder.productQuantityTextView.setText(String.valueOf(product.getQuantity()));

        holder.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = product.getQuantity();
                quantity++;
                product.setQuantity(quantity);
                holder.productQuantityTextView.setText(String.valueOf(quantity));
                updateTotalPrice();
            }
        });

        holder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = product.getQuantity();
                if (quantity > 1) {
                    quantity--;
                    product.setQuantity(quantity);
                    holder.productQuantityTextView.setText(String.valueOf(quantity));
                    updateTotalPrice();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        public TextView productNameTextView;
        public TextView productPriceTextView;
        public TextView productQuantityTextView;
        public TextView plusBtn;
        public TextView minusBtn;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.product_name_text_view);
            productPriceTextView = itemView.findViewById(R.id.product_price_text_view);
            productQuantityTextView = itemView.findViewById(R.id.productQ);
            plusBtn = itemView.findViewById(R.id.plusBtn);
            minusBtn = itemView.findViewById(R.id.minusBtn);
        }
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


}

