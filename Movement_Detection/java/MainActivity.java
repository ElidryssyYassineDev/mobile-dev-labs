package com.example.movementdetection;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity — Accelerometer sensor.
 *
 * The accelerometer measures the acceleration force applied to the device
 * on all three physical axes (X, Y, Z), including the force of gravity (9.81 m/s²).
 *
 * Typical uses:
 *  - Detecting shake gestures
 *  - Detecting device orientation / tilt
 *  - Counting steps (basic approach)
 *  - Motion-based games
 *
 * event.values layout:
 *  [0] = X axis — lateral acceleration (left/right)
 *  [1] = Y axis — vertical acceleration (up/down)
 *  [2] = Z axis — depth acceleration (forward/backward)
 *
 *  When the device lies flat on a table face-up: X≈0, Y≈0, Z≈9.81
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // ── Sensor components ─────────────────────────────────────────────────────
    private SensorManager sensorManager;
    private Sensor accelerometer;

    // ── UI ────────────────────────────────────────────────────────────────────
    private TextView tvX, tvY, tvZ;
    private TextView tvMagnitude;
    private TextView tvOrientation;
    private TextView tvAccuracy;

    // Gravity constant for reference
    private static final float GRAVITY = SensorManager.GRAVITY_EARTH; // 9.80665 m/s²

    // ─────────────────────────────────────────────────────────────────────────
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Wire up views
        tvX           = findViewById(R.id.tv_x);
        tvY           = findViewById(R.id.tv_y);
        tvZ           = findViewById(R.id.tv_z);
        tvMagnitude   = findViewById(R.id.tv_magnitude);
        tvOrientation = findViewById(R.id.tv_orientation);
        tvAccuracy    = findViewById(R.id.tv_accuracy);

        // ── 1. Obtain SensorManager ───────────────────────────────────────────
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // ── 2. Get the accelerometer Sensor ───────────────────────────────────
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometer == null) {
            tvOrientation.setText("Accelerometer not available on this device.");
        } else {
            tvOrientation.setText("Sensor ready: " + accelerometer.getName());
        }
    }

    // ── 3. Register listener when Activity is visible ─────────────────────────
    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            // SENSOR_DELAY_UI gives ~60ms updates — suitable for live UI display
            sensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_UI);
        }
    }

    // ── 4. Always unregister in onPause to save battery ───────────────────────
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // SensorEventListener callbacks
    // ═══════════════════════════════════════════════════════════════════════════

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Guard: only process accelerometer events
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) return;

        // Read the three axis values (m/s²)
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        // Display raw axis values
        tvX.setText(String.format("X (lateral):   %+.4f m/s²", x));
        tvY.setText(String.format("Y (vertical):  %+.4f m/s²", y));
        tvZ.setText(String.format("Z (depth):     %+.4f m/s²", z));

        // Compute vector magnitude: √(x²+y²+z²)
        // When at rest, this should be close to GRAVITY (~9.81 m/s²)
        float magnitude = (float) Math.sqrt(x * x + y * y + z * z);
        tvMagnitude.setText(String.format("Magnitude: %.4f m/s²  (gravity ≈ %.2f)", magnitude, GRAVITY));

        // Infer basic device orientation from dominant axis
        tvOrientation.setText("Orientation: " + inferOrientation(x, y, z));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        String label;
        switch (accuracy) {
            case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:   label = "HIGH";       break;
            case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM: label = "MEDIUM";     break;
            case SensorManager.SENSOR_STATUS_ACCURACY_LOW:    label = "LOW";        break;
            default:                                           label = "UNRELIABLE"; break;
        }
        tvAccuracy.setText("Accuracy: " + label);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /**
     * Determine approximate device orientation based on which axis
     * carries the most gravitational force.
     */
    private String inferOrientation(float x, float y, float z) {
        float absX = Math.abs(x);
        float absY = Math.abs(y);
        float absZ = Math.abs(z);

        if (absZ > absX && absZ > absY) {
            return z > 0 ? "Face up (flat)" : "Face down (flat)";
        } else if (absY > absX) {
            return y > 0 ? "Portrait (upright)" : "Portrait (upside-down)";
        } else {
            return x > 0 ? "Landscape (right)" : "Landscape (left)";
        }
    }
}