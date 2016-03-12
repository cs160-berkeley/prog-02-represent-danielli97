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

public class simple3activity extends AppCompatActivity {

    String s1 = "temp";
    String p1 = "temp";
    String e1 = "temp";
    String w1 = "temp";
    String t1 = "temp";
    String end1 = "temp";
    String bg1 = "L000551";
    String b1 = "Bills: "; //bioguide
    String com1 = "Commitees: "; //bioguide

    int toggle = 0;
    int leng = 0;

    private JSONArray representativesJSONArray;//coommiteees
    private JSONArray billsJSONArray;
    private JSONArray memesArray;

    String API_URL3 = "http://congress.api.sunlightfoundation.com/legislators/locate?";
    String API_KEY = "b090579dc143494d9a5b10a29bbb9049";
    String API_URL = "http://congress.api.sunlightfoundation.com/committees?member_ids=";
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
            toggle = 2;
            String urlzip = API_URL3 + "94704" + "&apikey=" + API_KEY;//senators
            new DownloadWebpageTask().execute(urlzip);
            toggle = 1;
            String urlzip1 = API_URL2 + "bg1" + "&apikey=" + API_KEY; //bills
            new DownloadWebpageTask().execute(urlzip1);
            toggle = 0;
            String urlzip2 = API_URL + "bg1" + "&apikey=" + API_KEY;
            new DownloadWebpageTask().execute(urlzip2);
        }



        //set detailed view
    }

    public String partyf(String s) {
        if (s.matches("R")) {
            return "Republican";
        } else if (s.matches("D")) {
            return "Democrat";
        } else if (s.matches("I")) {
            return "Independent";
        }
        return null;
    }
    public void senator1(JSONObject obj) throws JSONException {
        //set image
        end1 = obj.getString("term_end");
        bg1 = obj.getString("bioguide_id");
        System.out.println("bioguide " + bg1);

        ImageView iv1 = (ImageView) findViewById(R.id.d1);
        Picasso.with(getApplicationContext()).load("https://theunitedstates.io/images/congress/225x275/" +
                bg1 + ".jpg").into(iv1);

        s1 = obj.getString("title") +  " " + obj.getString("first_name") + " " + obj.getString("last_name");
        TextView sen1 = (TextView) findViewById(R.id.name);
        sen1.setText(s1);

        p1 = partyf(obj.getString("party"))+ ", " + obj.getString("state_name");
        TextView party1 = (TextView) findViewById(R.id.party1);
        party1.setText(p1);

        e1 = obj.getString("oc_email");
        TextView email1 = (TextView) findViewById(R.id.email1);
        email1.setText(e1);

        w1 = obj.getString("website");
        TextView website1 = (TextView) findViewById(R.id.website1);
        website1.setText(w1);

        t1 = obj.getString("twitter_id");
        TextView tweet1 = (TextView) findViewById(R.id.tweet1);
        tweet1.setText(t1);

        String end = obj.getString("end");
        TextView end1 = (TextView) findViewById(R.id.end);
        end1.setText(end);

    }


    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            if (true) {
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
                    if (toggle == 0) {
                        representativesJSONArray = JSONobj.getJSONArray("results");
                    }
                    if (toggle == 1) {
                        billsJSONArray = JSONobj.getJSONArray("results");
                    }
                    if (toggle == 2) {
                        memesArray = JSONobj.getJSONArray("results");
                    }
                    System.out.println(representativesJSONArray.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (billsJSONArray != null) {
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
            if (memesArray != null) {
                for (int i=0; i< memesArray.length() ;i++){
                    try {
                        JSONObject temp = (JSONObject) memesArray.get(i) ;
                        if (i == 0) {
                            senator1(temp);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
    //bills -> date and name
    //introduced_on + short_title

}