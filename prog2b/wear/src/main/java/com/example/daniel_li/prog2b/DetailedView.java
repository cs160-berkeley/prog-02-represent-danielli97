package com.example.daniel_li.prog2b;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Daniel-Li on 3/1/16.
 */
public class DetailedView extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailedview);
        Intent sendIntent = new Intent(this, WatchToPhoneService.class);
        startService(sendIntent);
    }
}
