package com.yassine.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView display;

    String current = "";
    double firstNumber = 0;
    String operator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
    }

    // Numbers
    public void onClickNumber(View view) {
        Button btn = (Button) view;
        current += btn.getText().toString();
        display.setText(current);
    }

    // Operators (+ - * /)
    public void onClickOperator(View view) {
        Button btn = (Button) view;

        if (!current.isEmpty()) {
            firstNumber = Double.parseDouble(current);
            operator = btn.getText().toString();
            current = "";
        }
    }

    // Equal =
    public void onClickEqual(View view) {
        if (current.isEmpty()) return;

        double secondNumber = Double.parseDouble(current);
        double result = 0;

        switch (operator) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
            case "/":
                if (secondNumber != 0)
                    result = firstNumber / secondNumber;
                else {
                    display.setText("Error");
                    return;
                }
                break;
        }

        display.setText(String.valueOf(result));
        current = String.valueOf(result);
    }

    // Clear
    public void onClickClear(View view) {
        current = "";
        firstNumber = 0;
        operator = "";
        display.setText("0");
    }
}