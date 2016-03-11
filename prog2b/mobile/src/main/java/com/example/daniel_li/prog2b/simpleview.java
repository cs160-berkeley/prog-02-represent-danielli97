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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class simpleview extends AppCompatActivity {

    String s1 = "temp";
    String p1 = "temp";
    String e1 = "temp";
    String w1 = "temp";
    String t1 = "temp";

    String s2 = "temp";
    String p2 = "temp";
    String e2 = "temp";
    String w2 = "temp";
    String t2 = "temp";

    String r1 = "temp";
    String p3 = "temp";
    String e3 = "temp";
    String w3 = "temp";
    String t3 = "temp";

    String r2 = "temp";
    String p4 = "temp";
    String e4 = "temp";
    String w4 = "temp";
    String t4 = "temp";


    String zipcode = "94704";

    String API_URL = "http://congress.api.sunlightfoundation.com/legislators/locate?";
    String API_KEY = "b090579dc143494d9a5b10a29bbb9049";
    String election = "https://raw.githubusercontent.com/cs160-sp16/voting-data/ master/election-county-2012.json";

    String lat;
    String lon;

    int count = 3;

    private JSONArray representativesJSONArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simpleview);
        Toolbar ab = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(ab);
        getSupportActionBar().setTitle("Congressional View");
        Intent intent = getIntent();

        //zipcode = intent.getStringExtra("type");

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        String type = intent.getStringExtra("type");
        System.out.println(type);
        zipcode = intent.getStringExtra("zip");
        System.out.println(zipcode);
       if (type.matches("zip")) {
           if (networkInfo != null && networkInfo.isConnected()) {
               zipcode = intent.getStringExtra("zip");
               System.out.println(zipcode);
               String urlzip = API_URL + "zip=" + zipcode + "&apikey=" + API_KEY;
               new DownloadWebpageTask().execute(urlzip);
           }
       } else {
           if (networkInfo != null && networkInfo.isConnected()) {
               lat = intent.getStringExtra("LAT");
               lon = intent.getStringExtra("LNG");
               String urlzip2 = API_URL + "latitude=" + lat +  "&longitude=" + lon + "&apikey=" + API_KEY;

               new DownloadWebpageTask().execute(urlzip2);


               //reverse geo coding from county


               //new DownloadWebpageTask().execute(election);

           }
       }
    }

    public void senator1(JSONObject obj) throws JSONException {
        //set image
        s1 = obj.getString("title") +  " " + obj.getString("first_name");
        TextView sen1 = (TextView) findViewById(R.id.sen1);
        sen1.setText(s1);

        p1 = obj.getString("party");
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
    }

    public void senator2(JSONObject obj) throws  JSONException {
        //set image
        s2 = obj.getString("title") +  " " + obj.getString("first_name");
        TextView sen2 = (TextView) findViewById(R.id.sen2);
        sen2.setText(s2);

        p2 = obj.getString("party");
        TextView party2 = (TextView) findViewById(R.id.party2);
        party2.setText(p2);

        e2 = obj.getString("oc_email");
        TextView email2 = (TextView) findViewById(R.id.email2);
        email2.setText(e2);

        w2 = obj.getString("website");
        TextView website2 = (TextView) findViewById(R.id.website2);
        website2.setText(w2);

        t2 = obj.getString("twitter_id");
        TextView tweet2 = (TextView) findViewById(R.id.tweet2);
        tweet2.setText(t2);
    }

    public void senator3(JSONObject obj) throws JSONException {
        //set image
        r1 = obj.getString("title") +  " " + obj.getString("first_name");
        TextView sen3 = (TextView) findViewById(R.id.sen3);
        sen3.setText(r1);

        p3 = obj.getString("party");
        TextView party3 = (TextView) findViewById(R.id.party3);
        party3.setText(p3);

        e3 = obj.getString("oc_email");
        TextView email3 = (TextView) findViewById(R.id.email3);
        email3.setText(e3);

        w3 = obj.getString("website");
        TextView website3 = (TextView) findViewById(R.id.website3);
        website3.setText(w3);

        t3 = obj.getString("twitter_id");
        TextView tweet3 = (TextView) findViewById(R.id.tweet3);
        tweet3.setText(t3);

    }

    public void senator4(JSONObject obj) throws  JSONException {
        //set image

        TextView sen3 = (TextView) findViewById(R.id.sen4);
        sen3.setText(r2);
        TextView party3 = (TextView) findViewById(R.id.party4);
        party3.setText(p4);
        TextView email3 = (TextView) findViewById(R.id.email4);
        email3.setText(e4);
        TextView website3 = (TextView) findViewById(R.id.website4);
        website3.setText(w4);
        TextView tweet3 = (TextView) findViewById(R.id.tweet4);
        tweet3.setText(t4);

    }

    public void detailedView1(View view)
    {
        //pack in intent shit
        TextView name = (TextView) findViewById(R.id.sen1);
        Intent intent = new Intent(this, simple2activity.class);
        intent.putExtra("name", name.toString());
        startActivity(intent);
    }

    public void detailedView2(View view)
    {
        //pack in intent shit
        TextView name = (TextView) findViewById(R.id.sen2);
        Intent intent = new Intent(this, simple2activity.class);
        intent.putExtra("name", name.toString());
        startActivity(intent);
    }

    public void detailedView3(View view)
    {
        //pack in intent shit
        TextView name = (TextView) findViewById(R.id.sen3);
        Intent intent = new Intent(this, simple2activity.class);
        intent.putExtra("name", name.toString());
        startActivity(intent);
    }

    public void detailedView4(View view)
    {
        //pack in intent shit
        TextView name = (TextView) findViewById(R.id.sen4);
        Intent intent = new Intent(this, simple2activity.class);
        intent.putExtra("name", name.toString());
        startActivity(intent);
    }



    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
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
            JSONObject JSONobj;
            if(response != null) {
                try {
                    JSONobj = (JSONObject) new JSONTokener(response).nextValue();
                    representativesJSONArray = JSONobj.getJSONArray("results");
                    System.out.println(representativesJSONArray.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (representativesJSONArray != null) {
                count = representativesJSONArray.length();
                for (int i=0; i<representativesJSONArray.length();i++){
                    try {
                        JSONObject temp = (JSONObject) representativesJSONArray.get(i) ;
                        if (i == 0) {
                            senator1(temp);
                        } else if (i == 1) {
                            senator2(temp);
                        } else if (i == 2) {
                            senator3(temp);
                        } else if (i == 3) {
                            senator4(temp);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
