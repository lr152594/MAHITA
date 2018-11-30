package com.example.firebear.mahita;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    // === GLOBAL VARIABLE DECLARATIONS === //
    LatLng POSITION = new LatLng(0.0, 0.0); // Near Africa

    Double longitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) mapFragment.getMapAsync(this);



        // ================================================================================================================== //
        // ================================================ GET LOCATION ==================================================== //
        // ================================================================================================================== //

        // LocationManager initialisation
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Check if GPS is enable
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            Log.d("=====> ", "GPS ENABLE");

            // Check location permission and request if it doesn't have it yet
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }

            // Get last GPS location
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            POSITION = new LatLng(location.getLatitude(), location.getLongitude());

        }
        else{
            Log.d("=====> ", "GPS DISABLE");
            Toast.makeText(getApplicationContext(), "Cannot get location - GPS disable", Toast.LENGTH_LONG).show();
        }
        // ================================================================================================================== //
        // ============================================== END GET LOCATION ================================================== //
        // ================================================================================================================== //
    }

    @Override
    public void onMapReady(GoogleMap map) {

        // SHOW LOCATION
        map.setMinZoomPreference(16); // Set the zoom
        map.addMarker(new MarkerOptions().position(POSITION).title("Lat : " + Double.toString(POSITION.latitude) + " | Long : " + Double.toString(POSITION.longitude))); // Add marker and coordinates in the label
        map.moveCamera(CameraUpdateFactory.newLatLng(POSITION)); // Set view on the marker
    }

}
