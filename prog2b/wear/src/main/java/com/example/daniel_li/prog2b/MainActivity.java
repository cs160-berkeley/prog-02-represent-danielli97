package com.example.daniel_li.prog2b;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.wearable.Wearable;
import com.squareup.seismic.ShakeDetector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity implements WearableListView.ClickListener {


    String sen1;
    String sen2;
    String rep;
    String state = "CA";
    String party = "Democrat";
    JSONArray representativesJSONArray;
    //private String[] s1 = {sen1, sen2, rep};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wear_activity_main);
        WearableListView listView = (WearableListView) findViewById(R.id.wearable_list);
        String data = getIntent().getExtras().getString("1");
        //populate list




        populatelist();
        String[] s1 = {sen1, sen2, rep};
        //assign an adpter
        System.out.println(s1.toString());
        listView.setAdapter(new Adapter(this, s1));


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        listView.setClickListener(this);

    }

    public void populatelist() {
        System.out.println("here");
        if (representativesJSONArray != null) {
            for (int i=0; i<representativesJSONArray.length();i++){
                try {
                    JSONObject temp = (JSONObject) representativesJSONArray.get(i) ;
                    if (i == 0) {
                        sen1 = temp.getString("title") +  " " + temp.getString("first_name") + " " + temp.getString("last_name");
                        System.out.println(sen1);
                    } else if (i == 1) {
                        sen2  = temp.getString("title") +  " " + temp.getString("first_name") + " " + temp.getString("last_name");
                        System.out.println(sen2);
                    } else if (i == 2) {
                        rep  = temp.getString("title") +  " " + temp.getString("first_name") + " " + temp.getString("last_name");
                        System.out.println(rep);
                    } else if (i == 3) {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onClick(WearableListView.ViewHolder v) {
        WatchToPhoneService.sendMessage("/test", "Good job!", this);
        Intent intent = new Intent(this, DetailedView.class );
        intent.putExtra("name", sen1);
        intent.putExtra("state", state);
        intent.putExtra("party", party);
        startActivity(intent);
    }
    public void onTopEmptyRegionClick() {

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
                    System.out.println("rep " + representativesJSONArray.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (representativesJSONArray != null) {
                for (int i=0; i<representativesJSONArray.length();i++){
                    try {
                        JSONObject temp = (JSONObject) representativesJSONArray.get(i) ;
                        if (i == 0) {
                            sen1 = temp.getString("title") +  " " + temp.getString("first_name") + " " + temp.getString("last_name");
                            System.out.println(sen1);
                        } else if (i == 1) {
                            sen2  = temp.getString("title") +  " " + temp.getString("first_name") + " " + temp.getString("last_name");
                            System.out.println(sen2);
                        } else if (i == 2) {
                            rep  = temp.getString("title") +  " " + temp.getString("first_name") + " " + temp.getString("last_name");
                            System.out.println(rep);
                        } else if (i == 3) {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }























    private static final class Adapter extends WearableListView.Adapter {
        private String[] mDataset;
        private final Context mContext;
        private final LayoutInflater mInflater;

        // Provide a suitable constructor (depends on the kind of dataset)
        public Adapter(Context context, String[] dataset) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
            mDataset = dataset;
        }

        // Provide a reference to the type of views you're using
        public static class ItemViewHolder extends WearableListView.ViewHolder {
            private TextView textView;
            public ItemViewHolder(View itemView) {
                super(itemView);
                // find the text view within the custom item's layout
                textView = (TextView) itemView.findViewById(R.id.name);
            }
        }

        // Create new views for list items
        // (invoked by the WearableListView's layout manager)
        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {

            // Inflate our custom layout for list items
            return new ItemViewHolder(mInflater.inflate(R.layout.list_item, null));
        }

        // Replace the contents of a list item
        // Instead of creating new views, the list tries to recycle existing ones
        // (invoked by the WearableListView's layout manager)
        @Override
        public void onBindViewHolder(WearableListView.ViewHolder holder,
                                     int position) {
            // retrieve the text view
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            TextView view = itemHolder.textView;
            // replace text contents
            view.setText(mDataset[position]);
            // replace list item's metadata
            holder.itemView.setTag(position);
        }

        // Return the size of your dataset
        // (invoked by the WearableListView's layout manager)
        @Override
        public int getItemCount() {
            return mDataset.length;
        }
    }
    //map crap







}
