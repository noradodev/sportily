package com.rado.spotilyapp.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rado.spotilyapp.DatabaseHelper;
import com.rado.spotilyapp.Product;
import com.rado.spotilyapp.ProductAdapter;
import com.rado.spotilyapp.R;

import java.util.ArrayList;
import java.util.List;


public class ProductFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_fregment, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        productList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(getActivity());

        // Fetch all products from the database
        productList = databaseHelper.getAllProducts();

        productAdapter = new ProductAdapter(getActivity(), productList);
        recyclerView.setAdapter(productAdapter);

        return view;
    }



}

