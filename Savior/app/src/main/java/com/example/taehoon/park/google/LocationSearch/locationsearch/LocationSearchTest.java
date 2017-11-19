package com.example.taehoon.park.google.LocationSearch.locationsearch;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.taehoon.park.R;

public class LocationSearchTest extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE_READ_EXTERNAL_STORAGE = 99;
    private TextView textView;
    private double lostLatitude =0;
    private double lostLongitude =0;
    private String addressString = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_CODE_READ_EXTERNAL_STORAGE);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }

        textView = (TextView) findViewById(R.id.tg1);

        GPSTracker gps = new GPSTracker(getApplicationContext());
        if(gps.canGetLocation()){
            lostLatitude = gps.getLatitude();
            lostLongitude = gps.getLongitude();
        }else{
            gps.showSettingsAlert();
        }

        CustomAddress address = new CustomAddress(getApplicationContext());
        addressString = address.findAddress(lostLatitude,lostLongitude);

        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("testt",lostLatitude +" / "+ lostLongitude + " : "+ addressString);
            }
        }, 2000);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE_READ_EXTERNAL_STORAGE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        }
    }
}
