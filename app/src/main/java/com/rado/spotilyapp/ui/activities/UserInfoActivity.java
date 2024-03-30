package com.rado.spotilyapp.ui.activities;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rado.spotilyapp.DatabaseHelper;
import com.rado.spotilyapp.R;
import com.rado.spotilyapp.User;
import com.rado.spotilyapp.UserAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserInfoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private DatabaseHelper dbHelper;
    private List<User> userList; // Original list of users
    private List<User> filteredList; // Filtered list based on search query


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);


        dbHelper = new DatabaseHelper(this);
        userList = dbHelper.getAllUsers();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userAdapter = new UserAdapter(this, userList);
        recyclerView.setAdapter(userAdapter);

        // Get reference to the SearchView
        SearchView searchView = findViewById(R.id.edit_search);

        // Set the query hint and set a listener for text changes
        searchView.setQueryHint("Search for user");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the user list based on the search query
                filterUsers(newText);
                return true;
            }
        });
    }




    protected void onResume() {
        super.onResume();
        // Reload user data from the database
        loadUserData();
    }

    private void loadUserData() {
        // Fetch user data from the database
        List<User> userList = dbHelper.getAllUsers();

        // Update the adapter with the new data
        userAdapter.updateData(userList);
    }


    private void filterUsers(String query) {
        // Filter the user list based on the search query
        List<User> filteredList = dbHelper.searchUsers(query);
        userAdapter.updateData(filteredList); // Update the adapter with the filtered list
    }










}
