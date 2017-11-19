package com.example.taehoon.park.basic.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.taehoon.park.R;

public class ServiceTest extends AppCompatActivity {

    private TextView stateService;
    private MyReceiver2 myReceiver2;
    private Button bntOk,bntCancel;
    private Vibrator vibe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);

        bntOk = (Button) findViewById(R.id.bnt_ok);
        bntCancel = (Button) findViewById(R.id.bnt_cancel);
        stateService = (TextView) findViewById(R.id.textview2);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        bntOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceTest.this, ServiceUtil.class);
                startService(intent);
            }
        });
        bntCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceTest.this, ServiceUtil.class);
                stopService(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        myReceiver2 = new MyReceiver2();
        registerReceiver(myReceiver2, new IntentFilter(ServiceUtil.MY_ALARM_ACTION));

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myReceiver2);
    }

    private class MyReceiver2 extends BroadcastReceiver { //비콘으로 부터 반환된 리시버
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            int datapassed = arg1.getIntExtra("DATAPASSED2", 0);

            String rssi = "RSSi : "+ datapassed;
            stateService.setText(rssi);
            if(datapassed < -65){
                vibe.vibrate(50);//뒤집힘 확인용도
            }
        }
    }

}
