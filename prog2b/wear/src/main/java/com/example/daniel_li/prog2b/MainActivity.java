package com.example.daniel_li.prog2b;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.wearable.Wearable;
import com.squareup.seismic.ShakeDetector;

public class MainActivity extends Activity implements WearableListView.ClickListener, ShakeDetector.Listener {


    private String[] s1 = {"Dianne Feinstein", "Dianne Feinstein", "Dianne Feinstein"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wear_activity_main);
        String data = getIntent().getExtras().getString("1");
        //populate list
        WearableListView listView = (WearableListView) findViewById(R.id.wearable_list);

        //assign an adpter
        listView.setAdapter(new Adapter(this, s1));


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        listView.setClickListener(this);

    }

    public void onClick(WearableListView.ViewHolder v) {
        WatchToPhoneService.sendMessage("/test", "Good job!", this);
        Intent intent = new Intent(this, DetailedView.class );
        startActivity(intent);
    }
    public void onTopEmptyRegionClick() {

    }

    public void hearShake() {
        Intent intent = new Intent(this, LocationActivity.class );
        startActivity(intent);
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
}
