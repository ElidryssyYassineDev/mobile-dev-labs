package com.example.movementdetection;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * HomeActivity — entry point of the application.
 * Provides navigation buttons to each of the three sensor activities.
 */
public class HomeActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Navigate to Accelerometer (MainActivity)
        findViewById(R.id.btn_accelerometer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        });

        // Navigate to Proximity
        findViewById(R.id.btn_proximity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ProximityActivity.class));
            }
        });

        // Navigate to Pedometer
        findViewById(R.id.btn_pedometer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, StepActivity.class));
            }
        });
    }
}