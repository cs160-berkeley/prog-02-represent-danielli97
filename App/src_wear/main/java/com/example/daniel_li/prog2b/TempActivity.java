package com.example.daniel_li.prog2b;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class TempActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Random rand = new Random();//rand
        int x = rand.nextInt(2);
        Log.d("T", String.valueOf(x));
        super.onCreate(savedInstanceState);



        if (getIntent().getExtras() == null) {

        }
        else if(getIntent().getExtras().getString("1") != null){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("1", getIntent().getExtras().getString("1"));
            startActivity(intent);
        } else if (x == 0){
            Intent intent = new Intent(this, LocationActivity2.class);
            intent.putExtra("2", getIntent().getExtras().getString("2"));
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LocationActivity.class);
            intent.putExtra("2", getIntent().getExtras().getString("2"));
            startActivity(intent);
        }
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void onSensorChanged(SensorEvent event) {
        float previous = 0;
        float current = 1;
        boolean flag = true;
        if (mAccelerometer != null) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            current = x + y + z;
            if (current != previous) {
                previous = current;
                //intent goes here
                System.out.println("current" + current);
                System.out.println("previous" + previous);
            }
        }
//        if (current == 6 && flag) {
//
//            Intent intent = new Intent(this, LocationActivity.class );
//            startActivity(intent);
//            flag = false;
//            System.out.println("sent");
//            System.out.println(flag);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

}
