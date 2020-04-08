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
import android.widget.TextView;

import java.util.List;

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
            text += String.valueOf(i) + ": " + String.valueOf(values[i]) + "\n";
        }
        TextView textView = findViewById(R.id.textViewSensors);
        textView.setText(text);
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

    public void showPicture(View view) {
        Intent intent = new Intent(this, DisplayPictureActivity.class);
        startActivity(intent);
    }
}
