package com.example.daniel_li.prog2b;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    private TextView locatedZipCode;
    private EditText inputtedZipCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locatedZipCode = (TextView) findViewById(R.id.currentlocation);
        inputtedZipCode = (EditText) findViewById(R.id.zipcode);

        getZip();
    }

    private void getZip()
    {
        //first set this to a test 94704
        locatedZipCode.setText("94704");
    }

    public void zip(View view)
    {
        Intent sendIntent = new Intent(this, PhoneToWatchService.class);
        sendIntent.putExtra("zip", "94704");
        startService(sendIntent);
        Intent intent = new Intent(this, simpleview.class);
        if (inputtedZipCode.getText().toString().length() == 5) {
            startActivity(intent);
        }
    }

    public void getLocation(View view)
    {
        Intent sendIntent = new Intent(this, PhoneToWatchService.class);
        sendIntent.putExtra("location", "94704");
        startService(sendIntent);
        Intent intent = new Intent(this, simpleview.class);
        startActivity(intent);
    }

}
