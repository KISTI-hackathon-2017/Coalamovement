package com.example.taehoon.park.google.LocationSearch.googlemaps;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.taehoon.park.R;
import com.example.taehoon.park.google.LocationSearch.locationsearch.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapsTest extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double lostLatitude =0;
    private double lostLongitude =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        GPSTracker gps = new GPSTracker(getApplicationContext());
        if(gps.canGetLocation()){
            lostLatitude = gps.getLatitude();
            lostLongitude = gps.getLongitude();
        }else{
            gps.showSettingsAlert();
        }
        LatLng current = new LatLng(lostLatitude, lostLongitude);
        mMap.addMarker(new MarkerOptions().position(current).title("You can call me Noh!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 20));
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(
                this, R.raw.mapstyle_grayscale);
        mMap.setMapStyle(style);
    }
}
