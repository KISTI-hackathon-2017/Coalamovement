package com.example.taehoon.park.test;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.taehoon.park.R;
import com.example.taehoon.park.basic.smsparsing.PermissionRequester;

public class Service_Disaster_test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__disaster_test);
        PermissionRequester.Builder requester = new PermissionRequester.Builder(this);
        requester.create().request(
                Manifest.permission.RECEIVE_SMS, 10000, new PermissionRequester.OnClickDenyButtonListener()
                {
                    @Override
                    public void onClick(Activity activity) {
                        Toast.makeText(Service_Disaster_test.this, "권한을 얻지 못했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
