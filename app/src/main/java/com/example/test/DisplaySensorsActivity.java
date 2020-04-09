package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static java.lang.Math.abs;

public class DisplaySensorsActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor acc, temp;
    private String textAcc, textTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_sensors);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        acc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        temp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        TextView textView = findViewById(R.id.textViewSensors);
        String text = String.valueOf(deviceSensors.size());

        if (acc != null) {
            textView.setText(acc.toString());
        } else {
            textView.setText("No accelerometer found");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //super.onAccuracyChanged();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] values = sensorEvent.values;
        String text = "Accelerometer\n";
        for (int i = 0; i < values.length; i ++) {
            text += i + ": " + values[i] + "\n";
        }
        TextView textView = findViewById(R.id.textViewSensors);
        textView.setText(text);

        ImageView imageView = findViewById(R.id.imageView3);
        float x = values[0];
        float y = values[1];

        if (abs(x) < abs(y)) {
            // display picture in portrait orientation
            if (y < 0) {
                // display picture up side down
                imageView.setRotation(180);
            } else {
                // display picture as default
                imageView.setRotation(0);
            }
        } else {
            // display picture in landscape orientation
            if (x < 0) {
                // display picture rotated 90 degrees counter clockwise
                imageView.setRotation(-90);
            } else {
                // display picture rotated 90 degrees clockwise
                imageView.setRotation(90);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}
