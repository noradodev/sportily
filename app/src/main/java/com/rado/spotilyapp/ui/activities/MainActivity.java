package com.rado.spotilyapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rado.spotilyapp.R;
import com.rado.spotilyapp.ui.activities.LoginActivity;
import com.rado.spotilyapp.ui.activities.RegisterActivity;
import com.rado.spotilyapp.SessionManager;
public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sessionManager = new SessionManager(this);

        // Check if the user is already logged in
        if (sessionManager.isLoggedIn()) {
            // Redirect to the appropriate activity based on user role
            redirectToAppropriateActivity();
            return; // Exit onCreate method to prevent MainActivity from being displayed
        }

        setContentView(R.layout.activity_main);



    }



    public void SignUpBtn(View view) {
        Intent toSignUp = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(toSignUp);
    }
    public void logInBtn(View view) {
        Intent toLogin = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(toLogin);
    }

    private void redirectToAppropriateActivity() {
        // Get the user role from the session manager
        String userRole = sessionManager.getUserRole();

        // Redirect based on the user role
        if (userRole.equals("admin")) {
            // Redirect to AdminActivity
            Intent intent = new Intent(getApplicationContext(), Action.class);
            startActivity(intent);
        } else {
            // Redirect to HomepageActivity for regular users
            Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
            startActivity(intent);
        }

        finish(); // Finish MainActivity so the user cannot go back to it
    }

//    private void navigateToHomepage() {
//        Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
//        startActivity(intent);
//        finish(); // Finish LoginActivity so user cannot go back to it after logging in
//    }
//
//    private void navigateToAdminPage() {
//        Intent intent = new Intent(MainActivity.this,  Action.class);
//        startActivity(intent);
//        finish(); // Finish LoginActivity so user cannot go back to it after logging in
//    }




}