package com.example.daniel_li.prog2b;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Daniel-Li on 3/1/16.
 */
public class DetailedView extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailedview);
        //populate();
        Intent intent = getIntent();
        String addMe = "";
        addMe = intent.getExtras().getString("name");
        String concatme = intent.getExtras().getString("party");
        addMe.concat(", " + concatme);
        System.out.println("addme" + addMe);
        TextView set = (TextView) findViewById(R.id.input);
        set.setText(addMe);

    }
    public void hearShake() {
        Intent intent = new Intent(this, LocationActivity.class );
        startActivity(intent);
    }
}
