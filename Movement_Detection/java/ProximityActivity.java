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
 * ProximityActivity — Proximity sensor.
 *
 * The proximity sensor measures the distance (in cm) between the device
 * front face and a nearby object. It is most commonly used to:
 *  - Turn off the screen when the phone is held to the ear during a call
 *  - Detect when the device is inside a pocket or bag
 *  - Wake the screen when the user's face moves away
 *
 * Most Android devices report a BINARY proximity value:
 *  - 0.0  → NEAR  (object detected — within a few centimetres)
 *  - maxRange (typically 5.0 cm) → FAR  (no object nearby)
 *
 * Some high-end sensors report a continuous distance in centimetres.
 *
 * event.values layout:
 *  [0] = distance in cm (or binary 0 / maxRange)
 *
 * No special permission is required for the proximity sensor.
 */
public class ProximityActivity extends AppCompatActivity implements SensorEventListener {

    // ── Sensor components ─────────────────────────────────────────────────────
    private SensorManager sensorManager;
    private Sensor proximitySensor;

    // Maximum range the sensor reports — used to decide NEAR vs FAR
    private float maxRange;

    // ── UI ────────────────────────────────────────────────────────────────────
    private TextView tvRawValue;
    private TextView tvState;        // "NEAR" or "FAR"
    private TextView tvMaxRange;
    private TextView tvSensorInfo;
    private TextView tvAccuracy;

    // ─────────────────────────────────────────────────────────────────────────
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);

        // Wire up views
        tvRawValue   = findViewById(R.id.tv_raw_value);
        tvState      = findViewById(R.id.tv_state);
        tvMaxRange   = findViewById(R.id.tv_max_range);
        tvSensorInfo = findViewById(R.id.tv_sensor_info);
        tvAccuracy   = findViewById(R.id.tv_accuracy);

        // ── 1. Obtain SensorManager ───────────────────────────────────────────
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // ── 2. Get the proximity Sensor ───────────────────────────────────────
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (proximitySensor == null) {
            tvSensorInfo.setText("Proximity sensor not available on this device.");
            tvState.setText("N/A");
        } else {
            maxRange = proximitySensor.getMaximumRange();
            tvSensorInfo.setText("Sensor: " + proximitySensor.getName()
                    + "\nVendor: " + proximitySensor.getVendor()
                    + "\nPower: " + proximitySensor.getPower() + " mA");
            tvMaxRange.setText("Max range: " + maxRange + " cm");
        }
    }

    // ── 3. Register in onResume ───────────────────────────────────────────────
    @Override
    protected void onResume() {
        super.onResume();
        if (proximitySensor != null) {
            // SENSOR_DELAY_NORMAL is sufficient for proximity — it doesn't change rapidly
            sensorManager.registerListener(this, proximitySensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    // ── 4. Unregister in onPause ──────────────────────────────────────────────
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
        if (event.sensor.getType() != Sensor.TYPE_PROXIMITY) return;

        // event.values[0] is the only meaningful value for proximity
        float distance = event.values[0];

        // Show raw reading
        tvRawValue.setText(String.format("Raw distance: %.2f cm", distance));

        // Determine NEAR or FAR
        // Convention: if the reading is less than maxRange, something is nearby.
        // Most devices return exactly 0 for NEAR and maxRange for FAR.
        boolean isNear = distance < maxRange;

        if (isNear) {
            tvState.setText("NEAR\nObject detected close to the sensor");
            tvState.setBackgroundColor(0xFFFFE0B2); // light orange
        } else {
            tvState.setText("FAR\nNo object nearby");
            tvState.setBackgroundColor(0xFFE8F5E9); // light green
        }
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
}