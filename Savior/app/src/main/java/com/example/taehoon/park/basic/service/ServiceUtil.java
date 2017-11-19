package com.example.taehoon.park.basic.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;

public class ServiceUtil extends Service {
    public final static String MY_ALARM_ACTION = "MY_ALARM_ACTION";
    public int count = 0;
    private MyThread myThread;
    private Thread thread;
    private WifiManager wifimanager;

    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method
        myThread = new MyThread();
        thread = new Thread(myThread);
        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.interrupt();
        thread = null;
        myThread = null;

    }

    class MyThread implements Runnable {
        @Override
        public void run() {
                try {
                    while(!Thread.currentThread().isInterrupted()) {

                        wifimanager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        WifiInfo info = wifimanager.getConnectionInfo();
                        int rssi = info.getRssi();
                        Intent intent = new Intent();
                        intent.setAction(MY_ALARM_ACTION);
                        intent.putExtra("DATAPASSED2", rssi);
                        sendBroadcast(intent); //서비스의 값을 메인엑티비티로 넘겨준다. 3초에 한번 송신.
                        Thread.sleep(1000);
                    }
                }catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {

                }
            }
        }
    }
