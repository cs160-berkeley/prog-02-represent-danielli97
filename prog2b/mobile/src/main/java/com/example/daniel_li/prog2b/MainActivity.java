package com.example.daniel_li.prog2b;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.common.ConnectionResult;


import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import io.fabric.sdk.android.Fabric;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "k02cEdVOqPeSL2Rgzn358VYjG";
    private static final String TWITTER_SECRET = "hczDbMta6UGCCGQcfHAK4SQcKRWZOlvfg6cNX1s4i1F3dH0CVJ";


    private String state;
    private String county;


    private TextView locatedZipCode;
    private EditText inputtedZipCode;
    private GoogleApiClient mGoogleApiClient;

    protected Location mLastLocation;
    protected TextView mLatitudeText;
    protected TextView mLongitudeText;
    private String lati;
    private String longi;
    Double lat = 0.0;
    Double lng = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        setContentView(R.layout.activity_main);
        //locatedZipCode = (TextView) findViewById(R.id.currentlocation);
        inputtedZipCode = (EditText) findViewById(R.id.zipcode);
//        Toolbar ab = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(ab);
//        getSupportActionBar().setTitle("Represent!");
        getZip();
    }

    //map shit
    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        //ok
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
            lati = mLatitudeText.toString();
            longi = mLongitudeText.toString();
            Double lat = Double.parseDouble(lati);
            Double lng = Double.parseDouble(longi);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connResult) {}


    public JSONObject getLocationInfo( double lat, double lng) {

        HttpGet httpGet = new HttpGet("http://maps.google.com/maps/api/geocode/json?latlng="+lat+","+lng+"&sensor=false");
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    JSONObject ret = getLocationInfo(lat, lng);
    JSONObject location;
    String location_string;

    public void jsonparse() {
        try {
            //Get JSON Array called "results" and then get the 0th complete object as JSON
            location = ret.getJSONArray("results").getJSONObject(0);
            // Get the value of the attribute whose name is "formatted_string"
            location_string = location.getString("formatted_address");
            Log.d("test", "formattted address:" + location_string);
        } catch (JSONException e1) {
            e1.printStackTrace();

        }
    }














    private void getZip()
    {
        //first set this to a test 94704
       // locatedZipCode.setText(loc);
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
        //map shit
}
