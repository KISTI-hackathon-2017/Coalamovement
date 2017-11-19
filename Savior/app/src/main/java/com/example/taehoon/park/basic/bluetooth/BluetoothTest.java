package com.example.taehoon.park.basic.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.taehoon.park.R;

public class BluetoothTest extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 99;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_test);

        if (mBluetoothAdapter == null) {
            //장치가 블루투스를 지원하지 않는 경우.
        } else {
            // 장치가 블루투스를 지원하는 경우.
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }


}