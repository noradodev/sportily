package com.rado.spotilyapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rado.spotilyapp.R;
import com.rado.spotilyapp.ui.activities.LoginActivity;
import com.rado.spotilyapp.ui.activities.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void SignUpBtn(View view) {
        Intent toSignUp = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(toSignUp);
    }
    public void LogInBtn(View view) {
        Intent toLogin = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(toLogin);
    }

}