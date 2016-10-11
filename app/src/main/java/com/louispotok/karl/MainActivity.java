package com.louispotok.karl;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.Manifest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    GoogleApiClient mGoogleApiClient;
    Location myLastLocation;
    String STORAGE_FILENAME = "karl_location_storage";

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

    @Override
    public void onConnected(Bundle connectionHint) {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            myLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
        }

        //TODO
        else {}
    }

    // TODO
    public void onConnectionFailed(ConnectionResult result) {}

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

    /* Called when the user clicks the "Send" button */
    public void saveLocation(View view) {

        // get location
        mGoogleApiClient.connect();
        double latitude = myLastLocation.getLatitude();
        double longitude = myLastLocation.getLongitude();
        long locationTimestamp = myLastLocation.getTime();
        

        // pass to the next activity
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", latitude);
        bundle.putDouble("longitude", longitude);
        bundle.putDouble("locationTimestamp", locationTimestamp);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
