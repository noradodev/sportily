package com.rado.spotilyapp.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rado.spotilyapp.R;
import com.rado.spotilyapp.databinding.ActivityMainBinding;
import com.rado.spotilyapp.ui.fragments.HomeFragment;

public class HomepageActivity extends AppCompatActivity {


    private HomeFragment homeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage); // Assuming your layout file is named activity_main.xml

        // Initialize fragments
        homeFragment = new HomeFragment();

        // Set the default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_home, homeFragment).commit();

        // Set up BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_home, homeFragment).commit();
                return true;
            } else {
                return false;
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if(id == R.id.new_group){
//            Toast.makeText(this, "create a new group", Toast.LENGTH_SHORT).show();
//        }
//        if(id == R.id.web_whatapp){
//            Toast.makeText(this, "create a new group", Toast.LENGTH_SHORT).show();
//        }
//        return true;
//    }
}