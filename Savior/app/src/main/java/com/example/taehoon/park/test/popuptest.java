package com.example.taehoon.park.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.taehoon.park.R;

public class popuptest extends AppCompatActivity {
    private WebView mWebView;
    private ProgressBar mProgressMid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popuptest);
        mProgressMid = (ProgressBar) findViewById(R.id.progressBar2);
        mProgressMid.setVisibility(ProgressBar.VISIBLE);
        mProgressMid.setIndeterminate(true);
        mProgressMid.setMax(100);
        Toast.makeText(getApplicationContext(),"정보를 가져오는 중입니다.",Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();
        String disaster = intent.getStringExtra("massa");
        String url = "http://www.safekorea.go.kr/idsiSFK/126/menuMap.do?w2xPath=/idsiSFK/wq/sfk/cs/contents/prevent/prevent01.xml";

        Log.d("testt","onCreate popup : " + disaster);
        if(disaster!=null || disaster!="") {
            switch (disaster) {
                case "1":
                    url = "http://www.safekorea.go.kr/idsiSFK/126/menuMap.do?w2xPath=/idsiSFK/wq/sfk/cs/contents/prevent/prevent09.xml";
                    break;
                case "2":
                    url = "http://www.safekorea.go.kr/idsiSFK/126/menuMap.do?w2xPath=/idsiSFK/wq/sfk/cs/contents/prevent/prevent07.xml";
                    break;
                case "3":
                    url = "http://www.safekorea.go.kr/idsiSFK/126/menuMap.do?w2xPath=/idsiSFK/wq/sfk/cs/contents/prevent/prevent02.xml";
                    break;
                case "4":
                    break;
            }
        }
        mWebView = (WebView) findViewById(R.id.wb);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Alert")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.confirm();
                                    }
                                })
                        .setCancelable(false)
                        .create()
                        .show();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message,
                                       final JsResult result) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Confirm")
                        .setMessage(message)
                        .setPositiveButton("Yes",
                                new AlertDialog.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.confirm();
                                    }
                                })
                        .setNegativeButton("No",
                                new AlertDialog.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.cancel();
                                    }
                                })
                        .setCancelable(false)
                        .create()
                        .show();
                return true;
            }
        });

        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgressMid.setVisibility(View.GONE);
            }
        }, 1500);

        mWebView.loadUrl(url);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Todo 웹페이지의 이전페이지가 있다면 이동할 수 있음
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
