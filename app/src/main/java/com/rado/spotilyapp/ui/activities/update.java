package com.rado.spotilyapp.ui.activities;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.rado.spotilyapp.DatabaseHelper;
import com.rado.spotilyapp.R;

public class update extends AppCompatActivity {
        private Button updateButton;
        private TextView valueTextView;
        private boolean isAddOperationSelected = true; // Flag for tracking selected operation

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin_add);

            // Set click listener for the update button
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateProduct();
                }
            });

            // Set click listener for the plus button
            findViewById(R.id.txtplus).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isAddOperationSelected) {
                        incrementValue();
                    } else {
                        decrementValue();
                    }
                }
            });

            // Set click listener for the minus button
            findViewById(R.id.txtminus).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isAddOperationSelected) {
                        decrementValue();
                    } else {
                        incrementValue();
                    }
                }
            });
        }

    private void updateProduct() {
        int value = Integer.parseInt(valueTextView.getText().toString());
         updateButton = findViewById(R.id.updateButton);
            valueTextView = findViewById(R.id.txtvalue);
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
           // boolean isSuccess = databaseHelper.updateProductValue(productId, updatedName, updatedPrice);

        if (isAddOperationSelected) {
            // Perform the update operation for addition
            int updatedValue = value + 1;
        } else {
            // Perform the update operation for subtraction
            if (value > 0) {
                int updatedValue = value - 1;
            }
        }
    }

        private void incrementValue() {
            int value = Integer.parseInt(valueTextView.getText().toString());
            value++;
            valueTextView.setText(String.valueOf(value));
        }

        private void decrementValue() {
            int value = Integer.parseInt(valueTextView.getText().toString());
            if (value > 0) {
                value--;
                valueTextView.setText(String.valueOf(value));
            }
        }

        private void toggleOperation() {
            isAddOperationSelected = !isAddOperationSelected;
            updateButton.setText(isAddOperationSelected ? "Add" : "Subtract");
        }
    }


