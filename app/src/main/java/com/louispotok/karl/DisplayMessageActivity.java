package com.louispotok.karl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(MainActivity.EXTRA_MESSAGE);
        Double latitude = bundle.getDouble("latitude");
        Double longitude = bundle.getDouble("longitude");
        String locationTimestamp = bundle.getString("locationTimestamp");

        TextView textView = new TextView(this);
        textView.setTextSize(20);
        String message = "Your latitude: " + latitude + "\nYour longitude: " + longitude + "\nTimestamp: " + locationTimestamp;
        textView.setText(message);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(textView);
    }
}
