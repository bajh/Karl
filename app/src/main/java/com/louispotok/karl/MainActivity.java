package com.louispotok.karl;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.Manifest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    // hm, should probably do something with this....
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    public static final String STORAGE_FILENAME = "karl_location_storage";

    // honestly don't know why this rigamarole is necessary
    private final int REQUEST_PERMISSION_ACCESS_FINE_LOCATION=1;

    GoogleApiClient mGoogleApiClient;
    Location myLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an instance of GoogleAPIClient.
         mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void getLocation() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            myLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
        }
        else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSION_ACCESS_FINE_LOCATION
            );
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        getLocation();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    // TODO
    public void onConnectionFailed(ConnectionResult result) {
    }

    // TODO
    public void onConnectionSuspended(int cause) {}

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private void writeToFile(String[] data) {
        try {
            StringBuilder stringBuilder = new StringBuilder("\n");
            for (String datum: data) {
                stringBuilder.append(datum).append(",");
            }
            String toWrite = stringBuilder.toString();
            FileOutputStream fos = openFileOutput(STORAGE_FILENAME, MODE_APPEND);
            fos.write(toWrite.getBytes());
            fos.close();
        }
        catch (IOException e) {
            //TODO
        }
    }

    /* Called when the user clicks the "Send" button */
    public void saveLocation(View view) {

        // the location may be out of date because we use the getLastLocation method
        // TODO: for higher accuracy, we could start requesting location updates

        String latitude = Double.toString(myLastLocation.getLatitude());
        String longitude = Double.toString(myLastLocation.getLongitude());
        String locationTimestamp = Long.toString(myLastLocation.getTime());
        String[] data = {latitude, longitude, locationTimestamp};

        // store to file

        writeToFile(data);

        // pass to the next activity
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("latitude", latitude);
        bundle.putString("longitude", longitude);
        bundle.putString("locationTimestamp", locationTimestamp);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
