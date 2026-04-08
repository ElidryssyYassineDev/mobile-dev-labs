package com.example.movementdetection;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * StepActivity — Pedometer using TYPE_STEP_COUNTER.
 *
 * Android provides two step-detection sensor types:
 *
 *  TYPE_STEP_COUNTER  — Reports the CUMULATIVE number of steps taken since
 *                        the device last rebooted. The value is a float but
 *                        represents a whole number of steps. Resets to 0 on
 *                        reboot. Hardware-based, very battery-efficient.
 *                        We use this to count steps in a session.
 *
 *  TYPE_STEP_DETECTOR — Fires a single event (value=1.0) each time a step
 *                        is detected. Lower latency, but less accurate for
 *                        counting over time. Not used here.
 *
 * Required permission (Android 10 / API 29 and above):
 *   android.permission.ACTIVITY_RECOGNITION
 *   → Requested at runtime in this activity.
 *
 * event.values layout:
 *  [0] = total steps since last reboot (cumulative float, e.g. 15382.0)
 */
public class StepActivity extends AppCompatActivity implements SensorEventListener {

    private static final int PERMISSION_REQUEST_ACTIVITY = 100;

    // ── Sensor components ─────────────────────────────────────────────────────
    private SensorManager sensorManager;
    private Sensor stepCounterSensor;

    // Session tracking
    // TYPE_STEP_COUNTER gives total steps since reboot.
    // We capture the value at session start and subtract to get session steps.
    private int stepsAtSessionStart = -1; // -1 = not yet initialised
    private int sessionSteps = 0;

    // ── UI ────────────────────────────────────────────────────────────────────
    private TextView tvSessionSteps;
    private TextView tvTotalSteps;
    private TextView tvDistance;
    private TextView tvCalories;
    private TextView tvSensorInfo;
    private TextView tvPermission;
    private Button   btnReset;

    // Constants for estimation
    private static final float STEP_LENGTH_M  = 0.762f; // average adult step ~76 cm
    private static final float CALORIES_PER_STEP = 0.04f; // rough estimate kcal/step

    // ─────────────────────────────────────────────────────────────────────────
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        // Wire up views
        tvSessionSteps = findViewById(R.id.tv_session_steps);
        tvTotalSteps   = findViewById(R.id.tv_total_steps);
        tvDistance     = findViewById(R.id.tv_distance);
        tvCalories     = findViewById(R.id.tv_calories);
        tvSensorInfo   = findViewById(R.id.tv_sensor_info);
        tvPermission   = findViewById(R.id.tv_permission);
        btnReset       = findViewById(R.id.btn_reset);

        // ── 1. Obtain SensorManager ───────────────────────────────────────────
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // ── 2. Get the Step Counter sensor ────────────────────────────────────
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepCounterSensor == null) {
            tvSensorInfo.setText("Step counter sensor not available on this device.\n"
                    + "Some emulators do not support this sensor.");
        } else {
            tvSensorInfo.setText("Sensor: " + stepCounterSensor.getName()
                    + "\nVendor: " + stepCounterSensor.getVendor()
                    + "\nMax range: " + (int) stepCounterSensor.getMaximumRange() + " steps");
        }

        // Reset session button
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Resetting means we'll re-capture the baseline on next event
                stepsAtSessionStart = -1;
                sessionSteps = 0;
                updateUI(sessionSteps, -1);
            }
        });

        // ── 3. Request ACTIVITY_RECOGNITION permission (Android 10+) ─────────
        checkAndRequestPermission();
    }

    // ── 4. Register listener when Activity is visible ─────────────────────────
    @Override
    protected void onResume() {
        super.onResume();
        registerStepSensor();
    }

    // ── 5. Unregister in onPause ──────────────────────────────────────────────
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
        if (event.sensor.getType() != Sensor.TYPE_STEP_COUNTER) return;

        // event.values[0] = cumulative steps since last device reboot
        int totalStepsSinceReboot = (int) event.values[0];

        // Capture baseline on first event of this session
        if (stepsAtSessionStart == -1) {
            stepsAtSessionStart = totalStepsSinceReboot;
        }

        // Session steps = current total minus the value when we started counting
        sessionSteps = totalStepsSinceReboot - stepsAtSessionStart;

        updateUI(sessionSteps, totalStepsSinceReboot);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Step counter accuracy rarely changes — no action needed
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // Permission handling
    // ═══════════════════════════════════════════════════════════════════════════

    private void checkAndRequestPermission() {
        // ACTIVITY_RECOGNITION is only required on Android 10 (API 29) and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACTIVITY_RECOGNITION)
                    != PackageManager.PERMISSION_GRANTED) {

                tvPermission.setText("Permission required: ACTIVITY_RECOGNITION\nRequesting…");
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                        PERMISSION_REQUEST_ACTIVITY);
            } else {
                tvPermission.setText("Permission: GRANTED");
            }
        } else {
            // Below API 29 no permission needed
            tvPermission.setText("Permission: not required (API < 29)");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_ACTIVITY) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                tvPermission.setText("Permission: GRANTED");
                registerStepSensor(); // register now that permission is granted
            } else {
                tvPermission.setText("Permission: DENIED — step counting unavailable");
            }
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void registerStepSensor() {
        if (stepCounterSensor == null) return;

        // Check permission before registering (required on API 29+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACTIVITY_RECOGNITION)
                    != PackageManager.PERMISSION_GRANTED) {
                return; // Will be registered after permission granted
            }
        }

        // TYPE_STEP_COUNTER is a wake-up sensor — it works even when the CPU sleeps.
        // SENSOR_DELAY_NORMAL is appropriate; steps don't change faster than ~3/s.
        sensorManager.registerListener(this, stepCounterSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void updateUI(int steps, int totalSinceReboot) {
        tvSessionSteps.setText("Session steps: " + steps);

        if (totalSinceReboot >= 0) {
            tvTotalSteps.setText("Total since reboot: " + totalSinceReboot);
        } else {
            tvTotalSteps.setText("Total since reboot: —");
        }

        // Estimated distance in metres and kilometres
        float distanceM = steps * STEP_LENGTH_M;
        if (distanceM < 1000) {
            tvDistance.setText(String.format("Distance: %.1f m", distanceM));
        } else {
            tvDistance.setText(String.format("Distance: %.2f km", distanceM / 1000f));
        }

        // Rough calorie estimate
        float calories = steps * CALORIES_PER_STEP;
        tvCalories.setText(String.format("Calories burned: ~%.1f kcal", calories));
    }
}