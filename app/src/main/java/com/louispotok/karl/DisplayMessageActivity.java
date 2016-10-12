package com.louispotok.karl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;

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
        Log.d("Karl","about to read");
        readFromFile();

    }

    private void readFromFile() {
        Log.d("Karl", "trying to read");
        try {
            StringBuffer buffer = new StringBuffer();
            FileInputStream inputStream = openFileInput(MainActivity.STORAGE_FILENAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String s;
            int i = 0;
            while((s = reader.readLine()) != null) {
                Log.d("Karl reading line " + Integer.toString(i) ,s);
                i += 1;
                buffer.append(s).append("\n");
            }
            inputStream.close();
        } catch (IOException er) {
            //TODO
            Log.d("Karl", "yuppers");
        }
    }

    public void returnHome(View view) {
        Intent outgoingIntent = new Intent(this, MainActivity.class);
        startActivity(outgoingIntent);
    }
}
