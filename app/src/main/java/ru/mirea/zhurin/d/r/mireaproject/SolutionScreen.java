package ru.mirea.zhurin.d.r.mireaproject;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SolutionScreen extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor magneticSensor;
    private Sensor accelerometerSensor;

    private TextView northTextView;

    private float[] magneticFieldValues = new float[3];
    private float[] accelerometerValues = new float[3];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_solution, container, false);

        northTextView = view.findViewById(R.id.north_text_view);

        sensorManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magneticFieldValues = event.values;
        } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerValues = event.values;
        }

        float[] rotationMatrix = new float[9];
        float[] inclinationMatrix = new float[9];
        boolean success = SensorManager.getRotationMatrix(rotationMatrix, inclinationMatrix, accelerometerValues, magneticFieldValues);

        if (success) {
            float[] orientationValues = new float[3];
            SensorManager.getOrientation(rotationMatrix, orientationValues);

            float azimuth = (float) Math.toDegrees(orientationValues[0]);
            if (azimuth < 0) {
                azimuth += 360;
            }

            northTextView.setText(String.format(getString(R.string.north_text), azimuth));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
