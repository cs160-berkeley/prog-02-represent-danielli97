package com.example.daniel_li.prog2b;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import com.squareup.seismic.ShakeDetector;


public class LocationActivity extends Activity {

    private SensorManager mSensorManager;

    private ShakeEventListener mSensorListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String location = getIntent().getExtras().getString("2");
        setContentView(R.layout.locationactivity);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {
                //randomEvent2();
            }
        });

    }
    public void randomEvent2() {
        Log.d("potatoes", "bullshitass planet");
        WatchToPhoneService.sendMessage("", "", this);
        Intent intent2 = new Intent(this, DetailedView.class );
        startActivity(intent2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

}
