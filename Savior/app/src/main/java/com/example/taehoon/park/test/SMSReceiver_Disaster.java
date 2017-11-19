package com.example.taehoon.park.test;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.taehoon.park.R;

import java.io.IOException;

/**
 * Created by taehoon on 2017-11-18.
 *
 *
 */
public class SMSReceiver_Disaster extends BroadcastReceiver {
    private static final String TAG = "SMSReceiver";
    private MediaPlayer music;
    private Vibrator vibe;
    private Context co;
    @Override
    public void onReceive(Context context, Intent intent) {
        co = context;
        music = MediaPlayer.create(co, R.raw.ms1);
        vibe = (Vibrator)co.getSystemService(Context.VIBRATOR_SERVICE);

        Log.d(TAG, "BroadcastReceiver Received");
        if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            Object[] messages = (Object[])bundle.get("pdus");
            SmsMessage[] smsMessage = new SmsMessage[messages.length];

            for(int i = 0; i < messages.length; i++) {
                smsMessage[i] = SmsMessage.createFromPdu((byte[])messages[i]);
            }

            String message = smsMessage[0].getMessageBody().toString();
            Log.d(TAG, "SMS Message: " + message);

            if(message.contains("기상청") || message.contains("지진") || message.contains("폭염") || message.contains("황사") || message.contains("태풍")){
                vibe.vibrate(10000);
                music.setLooping(true);
                music.start();
                Handler hd = new Handler();
                hd.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(music.isPlaying()){
                            // 음악을 정지합니다
                            music.stop();
                            try {
                                // 음악을 재생할경우를 대비해 준비합니다
                                // prepare()은 예외가 2가지나 필요합니다
                                music.prepare();
                            } catch (IllegalStateException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            music.seekTo(0);
                        }
                    }
                }, 10000);



                Intent popupIntent = new Intent(co, AlertDialogCustom.class).setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                popupIntent.putExtra("message",message.toString());
                PendingIntent pie= PendingIntent.getActivity(co, 0, popupIntent, PendingIntent.FLAG_ONE_SHOT);
                try {
                    pie.send();
                } catch (PendingIntent.CanceledException e) {

                }
            }
        }
    }

}
