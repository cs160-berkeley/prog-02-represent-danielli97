package com.example.daniel_li.prog2b;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class simple2activity extends AppCompatActivity {

    String s1 = "temp";
    String p1 = "temp";
    String e1 = "temp";
    String w1 = "temp";
    String t1 = "temp";
    String end1 = "temp";
    String bioguide = "temp";
    String b1 = "Bills: "; //bioguide
    String com1 = "Commitees: "; //bioguide

    int toggle = 0;
    int leng = 0;

    private JSONArray representativesJSONArray;
    private JSONArray billsJSONArray;

    String API_URL = "http://congress.api.sunlightfoundation.com/committees?member_ids=";
    String API_KEY = "b090579dc143494d9a5b10a29bbb9049";
    String API_URL2 = "http://congress.api.sunlightfoundation.com/bills?sponsor_id=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple2activity);
        Toolbar ab = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(ab);
        getSupportActionBar().setTitle("Detailed View");
        Intent intent = getIntent();
        //get remaining info
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            bioguide = intent.getStringExtra("bio");
            String urlzip = API_URL + bioguide + "&apikey=" + API_KEY;
            String urlzip2 = API_URL2 + bioguide + "&apikey=" + API_KEY;
            new DownloadWebpageTask().execute(urlzip);
            new DownloadWebpageTask().execute(urlzip2);
        }



        //set detailed view
        if (!intent.getStringExtra("null").matches("null")) {
            ImageView iv1 = (ImageView) findViewById(R.id.d);
            Picasso.with(getApplicationContext()).load("https://theunitedstates.io/images/congress/225x275/" +
                    bioguide + ".jpg").into(iv1);
            s1 = intent.getStringExtra("name");
            p1 = "Party: " + intent.getStringExtra("party");
            e1 = "Email: " + intent.getStringExtra("email");
            w1 = "Website: " + intent.getStringExtra("website");
            t1 = "Tweet: " + intent.getStringExtra("tweet");
            end1 = "End Date: " + intent.getStringExtra("end");
            detailPopulate();
        } else {

        }
    }

    public void detailPopulate() {
        //set image
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(s1);
        TextView party1 = (TextView) findViewById(R.id.party1);
        party1.setText(p1);
        TextView email1 = (TextView) findViewById(R.id.email1);
        email1.setText(e1);
        TextView website1 = (TextView) findViewById(R.id.website1);
        website1.setText(w1);
        TextView tweet1 = (TextView) findViewById(R.id.tweet1);
        tweet1.setText(t1);
        TextView end = (TextView) findViewById(R.id.end);
        end.setText(end1);
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            if (toggle == 1) {
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
                        toggle = 1;
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
                    toggle = 1;
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
            JSONObject JSONobj;
            if(response != null) {
                try {
                    JSONobj = (JSONObject) new JSONTokener(response).nextValue();
                    representativesJSONArray = JSONobj.getJSONArray("results");
                    if (toggle == 1) {
                        billsJSONArray = JSONobj.getJSONArray("results");
                    }
                    System.out.println(representativesJSONArray.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (billsJSONArray != null && toggle == 1) {
                for (int i = 0; i < billsJSONArray.length(); i++) {
                    try {
                        JSONObject temp = (JSONObject) billsJSONArray.get(i);

                        if (i < 3) {
                            b1 += temp.getString("introduced_on") + ", ";
                            if (temp.getString("short_title") != null) {
                                b1 += temp.getString("short_title");
                            } else {
                                b1 += temp.getString("official_title");
                            }
                        } else if (i == 3) {
                            b1 += temp.getString("introduced_on");
                            if (temp.getString("short_title") != null) {
                                b1 += temp.getString("short_title");
                            } else {
                                b1 += temp.getString("official_title");
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (representativesJSONArray != null) {
                for (int i=0; i< representativesJSONArray.length() ;i++){
                    try {
                        JSONObject temp = (JSONObject) representativesJSONArray.get(i) ;
                        if (i < 3) {
                            leng = representativesJSONArray.length();
                            com1 += temp.getString("name") + ", ";
                        } else if (i == 3) {
                            com1 += temp.getString("name");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(com1);
            TextView bill = (TextView) findViewById(R.id.bills);
            bill.setText(com1);
            System.out.println(b1);
            TextView committee = (TextView) findViewById(R.id.committees);
            committee.setText(b1);
        }
    }
    //bills -> date and name
    //introduced_on + short_title

}
