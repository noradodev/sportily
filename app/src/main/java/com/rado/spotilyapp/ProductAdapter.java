package com.rado.spotilyapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.rado.spotilyapp.ui.activities.ProductDetail;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private Context context;
    private DatabaseHelper databaseHelper;

//    Lina
private List<Product> filteredList; // New filtered list to store filtered items

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.databaseHelper = new DatabaseHelper(context);
//        lina
        this.filteredList = new ArrayList<>(productList); // Initialize filtered list with all items
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productTypeTextView.setText(product.getProductType());
        holder.productNameTextView.setText(product.getProductName());
        holder.productPriceTextView.setText(product.getPrice() + " $");

        // Handle item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add the product to the cart with quantity 1 (you can modify this as needed)
                long result = databaseHelper.addToCart(product.getId(), product.getProductName(), product.getPrice(),  1);
                if (result != -1) {
                    // Product added to cart successfully
                    // You can show a message or update UI as needed
                } else {
                    // Failed to add product to cart
                    // You can show an error message or handle the failure
                }

                // Navigate to ProductDetailActivity passing the product ID
                Intent intent = new Intent(context, ProductDetail.class);
                intent.putExtra("productId", product.getId());
                context.startActivity(intent);
            }
        });


        // Handle item click
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Navigate to ProductDetailActivity passing the product ID
//                Intent intent = new Intent(context, ProductDetail.class);
//                intent.putExtra("productId", product.getId());
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView productTypeTextView;
        public TextView productNameTextView;
        public TextView productPriceTextView;
        // Add other views as needed

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productTypeTextView = itemView.findViewById(R.id.product_type_text_view);
            productNameTextView = itemView.findViewById(R.id.product_name_text_view);
            productPriceTextView = itemView.findViewById(R.id.product_price_text_view);
            // Initialize other views
        }
    }

//    lina
// Method to filter the list based on the search query
public void filterList(List<Product> filteredList) {
    this.filteredList = filteredList;
    notifyDataSetChanged(); // Notify RecyclerView that dataset has changed
}

}
