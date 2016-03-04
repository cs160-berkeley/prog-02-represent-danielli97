package com.example.daniel_li.prog2b;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.squareup.seismic.ShakeDetector;


public class LocationActivity extends Activity implements ShakeDetector.Listener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String location = getIntent().getExtras().getString("2");
        setContentView(R.layout.locationactivity);

    }
    public void hearShake() {
        Intent intent = new Intent(this, LocationActivity2.class );
        startActivity(intent);
    }

}
