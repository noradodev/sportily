package com.rado.spotilyapp.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rado.spotilyapp.DatabaseHelper;
import com.rado.spotilyapp.R;
import com.rado.spotilyapp.User;
import com.rado.spotilyapp.UserAdapter;

import java.util.List;

public class UserInfoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Instantiate the DatabaseHelper class
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Fetch all users from the database
        List<User> userList = dbHelper.getAllUsers();

        // Initialize the UserAdapter with the userList
        userAdapter = new UserAdapter(this, userList);

        // Set the adapter for the RecyclerView
        recyclerView.setAdapter(userAdapter);
    }
}
