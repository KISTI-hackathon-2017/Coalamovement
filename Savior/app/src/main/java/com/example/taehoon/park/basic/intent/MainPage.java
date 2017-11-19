package com.example.taehoon.park.basic.intent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.taehoon.park.R;
import com.example.taehoon.park.basic.Intro.Intro;
import com.example.taehoon.park.basic.bluetooth.BluetoothTest;
import com.example.taehoon.park.basic.csv.CSVReaderTest;
import com.example.taehoon.park.basic.http.HttpRequestTest;
import com.example.taehoon.park.basic.json.JsonTest;
import com.example.taehoon.park.basic.lifecycle.LifeCycleTest;
import com.example.taehoon.park.basic.musicplayer.MusicPlayer;
import com.example.taehoon.park.basic.notificationparsing.NotificationParsingTest;
import com.example.taehoon.park.basic.service.ServiceTest;
import com.example.taehoon.park.basic.smsparsing.SMSParsingTest;
import com.example.taehoon.park.basic.webview.WebViewTest;
import com.example.taehoon.park.google.LocationSearch.googlemaps.GoogleMapsTest;
import com.example.taehoon.park.google.LocationSearch.locationsearch.LocationSearchTest;
import com.example.taehoon.park.test.Service_Disaster_test;

public class MainPage extends AppCompatActivity{

    Button btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_10,btn_11,btn_12,btn_13,btn_14;
    Button btn_g1,btn_g2,btn_g3,btn_g4,btn_g5,btn_g6,btn_g7,btn_g8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        btn_1 = (Button)findViewById(R.id.b1);
        btn_2 = (Button)findViewById(R.id.b2);
        btn_3 = (Button)findViewById(R.id.b3);
        btn_4 = (Button)findViewById(R.id.b4);
        btn_5 = (Button)findViewById(R.id.b5);
        btn_6 = (Button)findViewById(R.id.b6);
        btn_7 = (Button)findViewById(R.id.b7);
        btn_8 = (Button)findViewById(R.id.b8);
        btn_9 = (Button)findViewById(R.id.b9);
        btn_10 = (Button)findViewById(R.id.b10);
        btn_11 = (Button)findViewById(R.id.b11);
        btn_12 = (Button)findViewById(R.id.b12);
        btn_13 = (Button)findViewById(R.id.b13);
        btn_14 = (Button)findViewById(R.id.b14);
        btn_g1 = (Button)findViewById(R.id.g1);
        btn_g2 = (Button)findViewById(R.id.g2);
        btn_g3 = (Button)findViewById(R.id.g3);
        btn_g4 = (Button)findViewById(R.id.g4);
        btn_g5 = (Button)findViewById(R.id.g5);
        btn_g6 = (Button)findViewById(R.id.g6);
        btn_g7 = (Button)findViewById(R.id.g7);
        btn_g8 = (Button)findViewById(R.id.g8);

        btn_1.setOnClickListener(mClickListener);
        btn_2.setOnClickListener(mClickListener);
        btn_3.setOnClickListener(mClickListener);
        btn_4.setOnClickListener(mClickListener);
        btn_5.setOnClickListener(mClickListener);
        btn_6.setOnClickListener(mClickListener);
        btn_7.setOnClickListener(mClickListener);
        btn_8.setOnClickListener(mClickListener);
        btn_9.setOnClickListener(mClickListener);
        btn_10.setOnClickListener(mClickListener);
        btn_11.setOnClickListener(mClickListener);
        btn_12.setOnClickListener(mClickListener);
        btn_13.setOnClickListener(mClickListener);
        btn_14.setOnClickListener(mClickListener);
        btn_g1.setOnClickListener(mClickListener);
        btn_g2.setOnClickListener(mClickListener);
        btn_g3.setOnClickListener(mClickListener);
        btn_g4.setOnClickListener(mClickListener);
        btn_g5.setOnClickListener(mClickListener);
        btn_g6.setOnClickListener(mClickListener);
        btn_g7.setOnClickListener(mClickListener);
        btn_g8.setOnClickListener(mClickListener);
    }
    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.b1 :{
                    Intent intent = new Intent(MainPage.this,HttpRequestTest.class);
                    startActivity(intent);
                    break;
                }
                case R.id.b2 :{
                    Intent intent = new Intent(MainPage.this,JsonTest.class);
                    startActivity(intent);
                    break;
                }
                case R.id.b3 :{
                    Intent intent = new Intent(MainPage.this,LifeCycleTest.class);
                    startActivity(intent);
                    break;
                }
                case R.id.b4 :{
                    Intent intent = new Intent(MainPage.this,ServiceTest.class);
                    startActivity(intent);
                    break;
                }
                case R.id.b5 :{

                    break;
                }
                case R.id.b6 :{

                    break;
                }
                case R.id.b7 :{
                    Intent intent = new Intent(MainPage.this,Intro.class);
                    startActivity(intent);
                    break;
                }
                case R.id.b8 :{
                    Intent intent = new Intent(MainPage.this,WebViewTest.class);
                    startActivity(intent);
                    break;
                }
                case R.id.b9 :{
                    Intent intent = new Intent(MainPage.this,BluetoothTest.class);
                    startActivity(intent);
                    break;
                }
                case R.id.b10 :{
                    Intent intent = new Intent(MainPage.this,SMSParsingTest.class);
                    startActivity(intent);
                    break;
                }
                case R.id.b11 :{
                    Intent intent = new Intent(MainPage.this,MusicPlayer.class);
                    startActivity(intent);
                    break;
                }
                case R.id.b12 :{
                    Intent intent = new Intent(MainPage.this,NotificationParsingTest.class);
                    startActivity(intent);
                    break;
                }
                case R.id.b13 :{
                    Intent intent = new Intent(MainPage.this,CSVReaderTest.class);
                    startActivity(intent);
                    break;
                }
                case R.id.b14 :{
                    Intent intent = new Intent(MainPage.this,Service_Disaster_test.class);
                    startActivity(intent);
                    break;
                }
                case R.id.g1 :{
                    Intent intent = new Intent(MainPage.this,LocationSearchTest.class);
                    startActivity(intent);
                    break;
                }
                case R.id.g2 :{
                    Intent intent = new Intent(MainPage.this, GoogleMapsTest.class);
                    startActivity(intent);
                    break;
                }
                case R.id.g3 :{

                    break;
                }
                case R.id.g4 :{

                    break;
                }
                case R.id.g5 :{

                    break;
                }
                case R.id.g6 :{

                    break;
                }
                case R.id.g7 :{

                    break;
                }
                case R.id.g8 :{

                    break;
                }
            }
        }
    };

}
