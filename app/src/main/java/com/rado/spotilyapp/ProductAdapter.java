package com.rado.spotilyapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rado.spotilyapp.ui.activities.ProductDetail;

import java.io.File;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private Context context;
    private DatabaseHelper databaseHelper;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.databaseHelper = new DatabaseHelper(context);
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

        // Load product image using Glide
        String imagePath = product.getProductImagePath(); // Update to use the correct column name
        if (imagePath != null) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Glide.with(context)
                        .load(Uri.fromFile(imageFile))
                        .placeholder(R.drawable.mini_magick20240329_44002_snm4m2) // Placeholder image while loading
                        .error(R.drawable.ball) // Error image if Glide fails to load
                        .into(holder.grid_image);
            } else {
                // File does not exist, load placeholder image
                holder.grid_image.setImageResource(R.drawable.football);
            }
        } else {
            // Image path is null, load placeholder image
            holder.grid_image.setImageResource(R.drawable.logo);
        }

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
        public ImageView grid_image;
        // Add other views as needed

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productTypeTextView = itemView.findViewById(R.id.product_type_text_view);
            productNameTextView = itemView.findViewById(R.id.product_name_text_view);
            productPriceTextView = itemView.findViewById(R.id.product_price_text_view);
            grid_image = itemView.findViewById(R.id.grid_image);
            // Initialize other views
        }
    }
}
