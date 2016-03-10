package com.example.daniel_li.prog2b;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class simpleview extends AppCompatActivity {

    //private TextView sen1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simpleview);
        Toolbar ab = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(ab);
        getSupportActionBar().setTitle("Congressional View");
        Intent intent = getIntent();
        //getSupportActionBar().setTitle("Congressional View");
        senator1();
        senator2();
        representative();

    }

    public void detailedView(View view)
    {
        Intent intent = new Intent(this, simple2activity.class);
        startActivity(intent);
    }

    public void senator1() {
        TextView senator1name = (TextView) findViewById(R.id.sen1);
        senator1name.setText("Temp");

    }

    public void senator2() {
        TextView senator2name = (TextView) findViewById(R.id.sen2);
        senator2name.setText("Temp");
    }

    public void representative() {
        TextView rep1 = (TextView) findViewById(R.id.rep1);
        rep1.setText("Temp");
    }
}
