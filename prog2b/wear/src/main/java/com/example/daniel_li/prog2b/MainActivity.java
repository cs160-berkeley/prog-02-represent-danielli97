package com.example.daniel_li.prog2b;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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


    String sen1 = "temp";
    String sen2;
    String rep;
    String rep2;
    String p1;
    String p2;
    String p3;
    String p4;
    String state = "CA";
    String party = "Democrat";
    JSONArray representativesJSONArray;
    Boolean toggle = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wear_activity_main);
        WearableListView listView = (WearableListView) findViewById(R.id.wearable_list);
        Intent intent = getIntent();
        String parseMe = intent.getExtras().getString("1");
        String[] parsed = parseMe.split(",");
        System.out.println(parsed.toString());
        for (int i = 0; i < parsed.length; i++) {
            if (parsed[i] != null) {
                if (i == 0) {
                    sen1 = parsed[i];
                }
                if (i == 1) {
                    sen2 = parsed[i];
                }
                if (i == 2) {
                    rep = parsed[i];
                }
                if (toggle || i == 3) {
                    if (parsed[i] != null) {
                        toggle = true;
                        rep2 = parsed[i];
                    } else {
                        p1 = parsed[i];
                        if (i == 4) {
                            p1 = parsed[i];
                        }
                        if (i == 5) {
                            p2 = parsed[i];
                        }
                        if (i == 6) {
                            p3 = parsed[i];
                        }
                        if (i == 7) {
                            p4 = parsed[i];
                        }
                    }
                } else {
                    if (i == 3) {
                        p1 = parsed[i];
                    }
                    if (i == 4) {
                        p2 = parsed[i];
                    }
                    if (i == 5) {
                        p3 = parsed[i];
                    }
                }
            }
        }
        //populate list
        String[] s1 = {sen1, sen2, rep};
        System.out.println(s1.toString());
        //assign an adpter
        System.out.println(s1.toString());
        listView.setAdapter(new Adapter(this, s1));

        listView.setClickListener(this);

    }

    public void onClick(WearableListView.ViewHolder v) {
        WatchToPhoneService.sendMessage("/test", "Good job!", this);
        Intent intent = new Intent(this, DetailedView.class );
        intent.putExtra("name", sen1);
        intent.putExtra("party", p1);
        startActivity(intent);
    }
    public void onTopEmptyRegionClick() {

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
