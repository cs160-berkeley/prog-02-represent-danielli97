package com.example.daniel_li.prog2b;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import io.fabric.sdk.android.Fabric;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "k02cEdVOqPeSL2Rgzn358VYjG";
    private static final String TWITTER_SECRET = "hczDbMta6UGCCGQcfHAK4SQcKRWZOlvfg6cNX1s4i1F3dH0CVJ";


    private String state;
    private String county;
    private String ziplocated;

    //INPUTTED FINAL ZIPCODE
    String value;

    private EditText inputtedZipCode;
    private GoogleApiClient mGoogleApiClient;

    protected Location mLastLocation;

    String API_URL = "http://congress.api.sunlightfoundation.com/legislators/locate?";
    String API_KEY = "b090579dc143494d9a5b10a29bbb9049";


    //STRINGS FOR SENATOR NAMES AND COUNTY INFO
    JSONArray representativesJSONArray;
    String s1 = "temp";
    String s2 = "temp";
    String r1 = "temp";
    String r2 = "temp";
    //PARTIES
    String p1 = "temp";
    String p2 = "temp";
    String p3 = "temp";
    String p4 = "temp";
    //COUNTY STUFF TBD



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
        inputtedZipCode = (EditText) findViewById(R.id.zipcode);
        Toolbar ab = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(ab);
        getSupportActionBar().setTitle("Represent!");

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
      //  System.out.println("working");
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connResult) {
    }

    public void setSen(String response) {
        JSONObject JSONobj;
        if(response != null) {
            try {
                JSONobj = (JSONObject) new JSONTokener(response).nextValue();
                representativesJSONArray = JSONobj.getJSONArray("results");
                //System.out.println(representativesJSONArray.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (representativesJSONArray != null) {

            for (int i = 0; i < representativesJSONArray.length(); i++) {
                try {
                    JSONObject temp = (JSONObject) representativesJSONArray.get(i);
                    if (i == 0) {
                        s1 = temp.getString("title") + " " + temp.getString("first_name") + " " + temp.getString("last_name");
                        p1 = temp.getString("party");
                        System.out.println("asdf" + s1);
                    } else if (i == 1) {
                        s2 = temp.getString("title") + " " + temp.getString("first_name") + " " + temp.getString("last_name");
                        p2 = temp.getString("party");
                    } else if (i == 2) {
                        r1 = temp.getString("title") + " " + temp.getString("first_name") + " " + temp.getString("last_name");
                        p3 = temp.getString("party");
                    } else if (i == 3) {
                        r2 = temp.getString("title") + " " + temp.getString("first_name") + " " + temp.getString("last_name");
                        p4 = temp.getString("party");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    public void zip(View view) {
        String taskResult = "";

        value = String.valueOf(inputtedZipCode.getText());
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            System.out.println(value);
            String urlzip = API_URL + "zip=" + value + "&apikey=" + API_KEY;
            System.out.println(urlzip);
            //builds my string
            try {
                taskResult = new DownloadWebpageTask().execute(urlzip).get();
                System.out.println("this is my task result" + taskResult);
                setSen(taskResult);
            } catch (Exception e) {

            }
        }
        Intent sendIntent = new Intent(this, PhoneToWatchService.class);
        sendIntent.putExtra("s1", s1);
        sendIntent.putExtra("s2", s2);
        sendIntent.putExtra("r1", r1);
        sendIntent.putExtra("p1", p1);
        sendIntent.putExtra("p2", p2);
        sendIntent.putExtra("p3", p3);
        System.out.println("reps 3");
        if (!r2.matches("temp")) {
            sendIntent.putExtra("r2", r2);
            sendIntent.putExtra("p4", p4);
        }
        sendIntent.putExtra("zip", value);
        startService(sendIntent);
        Intent intent = new Intent(this, simpleview.class);
        if (inputtedZipCode.getText().toString().length() == 5) {
            intent.putExtra("type", "zip");
            intent.putExtra("zip", value);
            startActivity(intent);
        }
    }

    public void getLocation(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            double lat = mLastLocation.getLatitude();
            double lng = mLastLocation.getLongitude();
            System.out.println("latitude " + lat);
            System.out.println("longitude " + lng);
            ziplocated = "94704"; //for now


            //romney info
            String taskResult2;
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                System.out.println(value);
                String urlzip = "https://raw.githubusercontent.com/cs160-sp16/voting-data/master/election-county-2012.json";
                System.out.println(urlzip);
                //builds my string
                try {
                    taskResult2 = new DownloadWebpageTask().execute(urlzip).get();
                    System.out.println("this is my task result" + taskResult2);

                } catch (Exception e) {

                }
            }
            Intent sendIntent = new Intent(this, PhoneToWatchService.class);
            sendIntent.putExtra("location", ziplocated);
            String lat2 = String.valueOf(lat);
            String lng2 = String.valueOf(lng);
            sendIntent.putExtra("LAT", lat2);
            sendIntent.putExtra("LNG", lng2);
            startService(sendIntent);


            Intent intent = new Intent(this, simpleview.class);
            intent.putExtra("type", "coordinates");
            intent.putExtra("LAT", lat2);
            intent.putExtra("LNG", lng2);
            startActivity(intent);
        }
    }

    public class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    System.out.println(stringBuilder.toString());
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String response) {


        }

    }


}
