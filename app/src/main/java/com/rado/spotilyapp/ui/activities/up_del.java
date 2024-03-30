package com.rado.spotilyapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rado.spotilyapp.Product;
import com.rado.spotilyapp.R;

public class up_del extends AppCompatActivity {
    private StringBuffer fileToDelete;

    // Declare your variables and references here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_del);

        Button deleteButton = findViewById(R.id.button0);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the delete function
                deleteData();
            }
        });
    }

    private void deleteData() {
        // Perform the deletion operation here

        // Example: Deleting a file
        CardView cardView = findViewById(R.id.cardView0);
        cardView.setVisibility(View.GONE);
        Toast.makeText(this, "CardView deleted successfully", Toast.LENGTH_SHORT).show();
    }
}
