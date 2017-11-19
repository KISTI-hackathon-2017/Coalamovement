package com.example.taehoon.park.test;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taehoon.park.R;
import com.example.taehoon.park.basic.http.HttpUtil;
import com.example.taehoon.park.google.LocationSearch.locationsearch.CustomAddress;
import com.example.taehoon.park.google.LocationSearch.locationsearch.GPSTracker;
import com.opencsv.CSVReader;

import java.io.InputStreamReader;
import java.util.ArrayList;

public class AlertDialogCustom extends AppCompatActivity {
    private static PowerManager.WakeLock mWakeLock;
    private WebView mWebView;
    private String sX="",sY="",eX="",eY="";
    private static final String TAG= "HttpRequestTest";
    private ScrollView scrollView;
 /// 33.245983, 126.390797
    private ImageView imageView1, imageView2,imageView3;
    private TextView textView;
    private ProgressBar mProgressMid;
    private Drawable drawable;
    private ArrayList<String[]> collection;
    private int exnum = 6, eynum = 5;
    private String disaster="";
    private double lostLatitude =0;
    private double lostLongitude =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog_custom);

        mProgressMid = (ProgressBar) findViewById(R.id.progressBar2);
        mProgressMid.setVisibility(ProgressBar.VISIBLE);
        mProgressMid.setIndeterminate(true);
        mProgressMid.setMax(100);
        Toast.makeText(getApplicationContext(),"CAUTION!!!!",Toast.LENGTH_SHORT).show();
        textView = (TextView) findViewById(R.id.tx1);
        scrollView = (ScrollView) findViewById(R.id.sc);
        imageView1 = (ImageView) findViewById(R.id.but1);
        imageView2 = (ImageView) findViewById(R.id.but2);
        imageView3 = (ImageView) findViewById(R.id.im1);

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, 0);

            }
        });

        // todo 현재 위치
        scrollView.fullScroll(ScrollView.FOCUS_UP);
        scrollView.scrollTo(0, 0);
        scrollView.smoothScrollTo(0,0);
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        }, 600);

        Intent intent = getIntent();
        disaster = intent.getStringExtra("message");

        if(disaster.contains("지진")){
            textView.setText("Earthquake warning".toUpperCase());
            drawable  = getResources().getDrawable(R.drawable.el);
            imageView3.setImageDrawable(drawable);
        }else if(disaster.contains("폭염")){
            textView.setText("Heatwave warning".toUpperCase());
            drawable  = getResources().getDrawable(R.drawable.fire);
            imageView3.setImageDrawable(drawable);
        }else if(disaster.contains("황사")){
            textView.setText("Duststorm warning".toUpperCase());
        }else if(disaster.contains("태풍")){
            textView.setText("Typhoon warning".toUpperCase());
            drawable  = getResources().getDrawable(R.drawable.typ);
            imageView3.setImageDrawable(drawable);
        }

        GPSTracker gps = new GPSTracker(getApplicationContext());
        if(gps.canGetLocation()){
            lostLatitude = gps.getLatitude();
            lostLongitude = gps.getLongitude();
        }else{
            gps.showSettingsAlert();
        }
        //lostLatitude = 35.231004;
       // lostLongitude = 129.082444;
        String url = "http://map.daum.net/link/map/"+lostLatitude+","+lostLongitude;
        String met = "GET";
        try {
            final HttpUtil task;
            task = new HttpUtil(url,met);
            task.execute();
            Log.d(TAG,"taskexecution : " + url + " "+met);

            Handler hd = new Handler();
            hd.postDelayed(new Runnable() {
                @Override
                public void run() {
                    sX = task.getX();
                    sY = task.getY();
                    Log.d(TAG,sX + "," + sY);
                    Log.d(TAG,"taskexecution : " + task.getResult());

                }
            }, 1500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String addressString = "";
        CustomAddress address = new CustomAddress(getApplicationContext());
        addressString = address.findAddress(lostLatitude,lostLongitude);


        if(disaster.contains("지진")){
            collection= readPatternCSVLineByLine("outside_shelter.csv",addressString);
            exnum = 6;
            eynum = 5;
        }else if(disaster.contains("폭염")){
            collection= readPatternCSVLineByLine("heat_wave_shelter.csv",addressString);
            exnum = 17;
            eynum = 18;
        }else if(disaster.contains("황사")){
            collection= readPatternCSVLineByLine("heat_wave_shelter.csv",addressString);
            exnum = 17;
            eynum = 18;
        }else if(disaster.contains("태풍")){
            collection= readPatternCSVLineByLine("inside_shelter.csv",addressString);
            exnum = 6;
            eynum = 5;
        }


        String ex = "", ey = "";
        if(!collection.isEmpty()) {
            ex  = collection.get(1)[exnum];
            ey = collection.get(1)[eynum];
            Log.d("test2",sX+","+sY+"/"+ ex + "," + ey);
        }
        double min = 1000000.0;

        for (String[] e : collection) {
            if(e[exnum]!="" || e[eynum]!="") {
                Log.d("test2", e[0] + "," + e[1] + "," + e[2] + "," + e[3] + "," + e[4]  + "," + e[exnum] + "," + e[eynum]);
                double current = gps.getDistance(lostLatitude, lostLongitude, Double.parseDouble(e[exnum]), Double.parseDouble(e[eynum]));
                if (min > current) {
                    min = current;
                    ex = e[exnum];
                    ey = e[eynum];
                }
            }
        }


        // todo 도착 위치 - csv파일 분석후 가장 가까운 위경도.

        String url2 = "http://map.daum.net/link/map/"+ex+","+ey;
        String met2 = "GET";
        try {
            final HttpUtil task2;
            task2 = new HttpUtil(url2,met2);
            task2.execute();
            Log.d(TAG,"taskexecution : " + url2 + " "+met2);

            Handler hd = new Handler();
            hd.postDelayed(new Runnable() {
                @Override
                public void run() {
                    eX = task2.getX();
                    eY = task2.getY();
                    Log.d(TAG,eX + "," + eY);
                }
            }, 1500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        mWebView = (WebView) findViewById(R.id.wb);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);



        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                Log.d(TAG,"taskexecution : " +"http://map.daum.net/?sX="+sX+"&sY="+sY+"&sName=현재위치&eX="+eX+"&eY="+eY+"&eName=비상대피소");

                // mWebView.loadUrl("https://www.ebay.com/sch/i.html?_from=R40&_trksid=p2050601.m570.l1313.TR0.TRC0.H0.Xwater.TRS0&_nkw=water&_sacat=0#");
                mWebView.loadUrl("http://map.daum.net/?sX="+sX+"&sY="+sY+"&sName=현재위치&eX="+eX+"&eY="+eY+"&eName=비상대피소");

                mProgressMid.setVisibility(ProgressBar.GONE); // 진행바 중간
            }
        }, 2500);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.postDelayed( new Runnable(){
                    @Override
                    public void run() {
                        scrollView.smoothScrollBy(0, 3000);
                    }
                }, 500);

            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlertDialogCustom.this, StartPage.class);
                intent.putExtra("massage2",disaster);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

    }
    public ArrayList<String[]> readPatternCSVLineByLine(String filename,String sublocality){
        ArrayList<String[]> rows = new ArrayList<String[]>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(getAssets().open(filename),"euc-kr"));
            String [] nextLine;;
            while ((nextLine = reader.readNext()) != null) {
                if(nextLine[0].equals(sublocality)) {
                    rows.add(nextLine);
                }
            }
        } catch (Exception e) {
            Log.e("File", "Error while read Pattern CSV file : " + e);
        }
        return rows;
    }

    @Override
    public void onPause() {
        super.onPause();
        scrollView.fullScroll(ScrollView.FOCUS_UP);
        scrollView.scrollTo(0, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        scrollView.fullScroll(ScrollView.FOCUS_UP);
        scrollView.scrollTo(0, 0);
    }

}
