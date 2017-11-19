package com.example.taehoon.park.basic.http;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.taehoon.park.R;

public class HttpRequestTest extends AppCompatActivity {
    private static final String TAG= "HttpRequestTest";

    public String fixurl = "http://openapi.meis.go.kr/rest/datalist?id=WemoData&sdate=2012-01-01&edate=2012-01-01&sta_cde=213";

    public TextView textView;
    public Button get,put,post;
    public EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_httprequest);
        textView = (TextView) findViewById(R.id.retrun);
        get = (Button) findViewById(R.id.get);
        put = (Button) findViewById(R.id.put);
        post = (Button) findViewById(R.id.post);
        editText = (EditText) findViewById(R.id.url);
        editText.setText(fixurl);

        get.setOnClickListener(mClickListener);
        put.setOnClickListener(mClickListener);
        post.setOnClickListener(mClickListener);
    }

    private void taskexecution(String url, String met) {
        try {
            final HttpUtil task;
            task = new HttpUtil(url,met);
            task.execute();
            Log.d(TAG,"taskexecution : " + url + " "+met);

            Handler hd = new Handler();
            hd.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textView.setText(task.getResult());
                }
            }, 1500);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String url = editText.getText().toString();
            String method = "GET"; // default
            switch (v.getId()) {
                case R.id.get: {
                    method = "GET";
                    break;
                }
                case R.id.put: {
                    method = "PUT";
                    break;
                }
                case R.id.post: {
                    method = "POST";
                    break;
                }
            }
            taskexecution(url,method);
        }
    };
}