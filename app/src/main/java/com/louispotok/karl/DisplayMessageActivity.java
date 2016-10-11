package com.louispotok.karl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent incomingIntent = getIntent();
        Bundle bundle = incomingIntent.getExtras();
        String latitude = bundle.getString("latitude");
        String longitude = bundle.getString("longitude");
        String locationTimestamp = bundle.getString("locationTimestamp");

        String message = "Your latitude: " + latitude + "\nYour longitude: " + longitude + "\nTimestamp: " + locationTimestamp;
        TextView textView = (TextView) findViewById(R.id.show_current_location);
        textView.setText(message);

    }

    public void returnHome(View view) {
        Intent outgoingIntent = new Intent(this, MainActivity.class);
        startActivity(outgoingIntent);
    }
}
