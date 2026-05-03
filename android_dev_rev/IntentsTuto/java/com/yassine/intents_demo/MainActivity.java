package com.yassine.intents_demo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText name;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.etname);
    }

    public void clickSubmit(View view){



            if(name.getText().toString().isEmpty()){
                Toast toast = Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT);
                toast.show();
            }
            else{
                Intent intent = new Intent(this, SecondActivity.class);
                intent.putExtra("name", name.getText().toString());
                startActivity(intent);
            }



    }
}