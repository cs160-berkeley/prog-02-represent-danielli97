package com.example.daniel_li.prog2b;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class simple2activity extends AppCompatActivity {

    String s1 = "temp";
    String p1 = "temp";
    String e1 = "temp";
    String w1 = "temp";
    String t1 = "temp";
    String end1 = "temp";
    String b1 = "temp"; //bioguide
    String com1 = "temp"; //bioguide
    ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple2activity);
        Toolbar ab = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(ab);
        getSupportActionBar().setTitle("Detailed View");
        Intent intent = getIntent();
        //set detailed view
        detailPopulate();
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
        TextView bill = (TextView) findViewById(R.id.bills);
        bill.setText(b1);
        TextView committee = (TextView) findViewById(R.id.committees);
        committee.setText(com1);

    }
}
