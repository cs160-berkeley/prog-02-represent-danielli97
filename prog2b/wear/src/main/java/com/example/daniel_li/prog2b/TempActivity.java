package com.example.daniel_li.prog2b;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class TempActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Random rand = new Random();//rand
        int x = rand.nextInt(2);
        Log.d("T", String.valueOf(x));
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() == null) {

        }
        else if(getIntent().getExtras().getString("1") != null){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("1", getIntent().getExtras().getString("1"));
            startActivity(intent);
        } else if (x == 0){
            Intent intent = new Intent(this, LocationActivity2.class);
            intent.putExtra("2", getIntent().getExtras().getString("2"));
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LocationActivity.class);
            intent.putExtra("2", getIntent().getExtras().getString("2"));
            startActivity(intent);
        }

    }

}
