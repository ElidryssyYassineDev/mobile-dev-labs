package com.yassine.quadraticequationsolver;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText a, b, c;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        a = findViewById(R.id.a);
        b = findViewById(R.id.b);
        c = findViewById(R.id.c);
        result = findViewById(R.id.result);
    }

    public void clicCalculate(View view) {
        String strA = a.getText().toString().trim();
        String strB = b.getText().toString().trim();
        String strC = c.getText().toString().trim();

        if (strA.isEmpty() || strB.isEmpty() || strC.isEmpty()) {
            Toast.makeText(this, getString(R.string.empty_fields), Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double labelA = Double.parseDouble(strA);
            double labelB = Double.parseDouble(strB);
            double labelC = Double.parseDouble(strC);

            if (labelA == 0) {
                result.setText(getString(R.string.not_quadratic));
                return;
            }

            double delta = (labelB * labelB) - (4 * labelA * labelC);

            if (delta > 0) {
                double x1 = (-labelB + Math.sqrt(delta)) / (2 * labelA);
                double x2 = (-labelB - Math.sqrt(delta)) / (2 * labelA);
                result.setText(getString(R.string.apres_clic, x1, x2));
            } else if (delta == 0) {
                double x = -labelB / (2 * labelA);
                result.setText(getString(R.string.unique_solution, x));
            } else {
                result.setText(getString(R.string.no_solution));
            }
        } catch (NumberFormatException e) {
            result.setText(getString(R.string.invalid_input));
        }
    }
}
