package com.example.taehoon.park.basic.smsparsing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by taehoon on 2017-11-18.
 *
 *
 *
 * <receiver android:name=".testfolder.SMSReceiver_Disaster"
 android:enabled="true"
 android:exported="true">
 <intent-filter>
 <action android:name="android.provider.Telephony.SMS_RECEIVED" />
 </intent-filter>
 </receiver>
 */
public class SMSReceiver extends BroadcastReceiver {
    private static final String TAG = "SMSReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
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

        }
    }

}
