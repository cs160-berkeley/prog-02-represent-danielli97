package com.example.daniel_li.prog2b;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private TextView locatedZipCode;
    private EditText inputtedZipCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locatedZipCode = (TextView) findViewById(R.id.currentlocation);
        inputtedZipCode = (EditText) findViewById(R.id.zipcode);

        getZip();
    }

    private void getZip()
    {
        //first set this to a test 94704
        locatedZipCode.setText("94704");
    }

    public void zip(View view)
    {
        Intent intent = new Intent(this, simpleview.class);
        if (inputtedZipCode.getText().toString().length() == 5) {
            startActivity(intent);
        }
    }

    public void getLocation(View view)
    {
        Intent intent = new Intent(this, simpleview.class);
        startActivity(intent);
    }
}
