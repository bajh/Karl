package com.louispotok.karl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Log.d("Karl", "got bundle!");
        String latitude = bundle.getString("latitude");
        String longitude = bundle.getString("longitude");
        String locationTimestamp = bundle.getString("locationTimestamp");
        Log.d("Karl", "no chance, right?");
        TextView textView = new TextView(this);
        textView.setTextSize(20);
        String message = "Your latitude: " + latitude + "\nYour longitude: " + longitude + "\nTimestamp: " + locationTimestamp;
        textView.setText(message);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(textView);
    }
}
