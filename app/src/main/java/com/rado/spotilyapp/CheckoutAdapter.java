package com.rado.spotilyapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rado.spotilyapp.Product;
import com.rado.spotilyapp.R;
import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder> {

    private List<Product> cartItemList;

    public CheckoutAdapter(List<Product> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkout, parent, false);
        return new CheckoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutViewHolder holder, int position) {
        Product product = cartItemList.get(position);
        holder.productNameTextView.setText(product.getProductName());
        holder.productPriceTextView.setText("$" + product.getPrice());
        holder.productQuantityTextView.setText(String.valueOf(product.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class CheckoutViewHolder extends RecyclerView.ViewHolder {
        public TextView productNameTextView;
        public TextView productPriceTextView;
        public TextView productQuantityTextView;

        public CheckoutViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.product_name_text_view);
            productPriceTextView = itemView.findViewById(R.id.product_price_text_view);
            productQuantityTextView = itemView.findViewById(R.id.product_quantity_text_view);
        }
    }
}
