package com.example.taehoon.park.test;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taehoon.park.R;
import com.example.taehoon.park.google.LocationSearch.locationsearch.CustomAddress;
import com.example.taehoon.park.google.LocationSearch.locationsearch.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.opencsv.CSVReader;

import java.io.InputStreamReader;
import java.util.ArrayList;

public class StartPage extends AppCompatActivity implements OnMapReadyCallback {
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private static final int PERMISSIONS_REQUEST_CODE_READ_EXTERNAL_STORAGE = 99;
    private ProgressBar mProgressMid;
    private GoogleMap mMap;
    private WebView mWebView2;
    private String sX="",sY="",eX="",eY="";
    private static final String TAG= "HttpRequestTest";
    private String disaster;
    private double lostLatitude =0;
    private double lostLongitude =0;
    private ArrayList<String[]> collection;
    private boolean flag;
    private ImageView im1,im2,im3,im4;
    private int exnum = 6, eynum = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startpage);
        mProgressMid = (ProgressBar) findViewById(R.id.progressBar2);
        mProgressMid.setVisibility(ProgressBar.VISIBLE);
        mProgressMid.setIndeterminate(true);
        mProgressMid.setMax(100);
        Toast.makeText(getApplicationContext(),"정보를 가져오는 중입니다.",Toast.LENGTH_SHORT).show();

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);

        Intent intent = getIntent();
        disaster = intent.getStringExtra("massage2");
        Log.d("testt","onCreate : " + disaster);



        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.activity_shelter,
                R.layout.welcome_slide2};

        addBottomDots(1);
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter(getApplicationContext());
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1, false);

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // num.setText("UPDATE...");

            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE_READ_EXTERNAL_STORAGE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        }
    }



    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            //Todo 여기서 페이지가 달라질때의 액션을 줄수있다.
            // changing the next button text 'NEXT' / 'GOT IT'
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }



    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        public MyViewPagerAdapter(Context c) {
            layoutInflater = LayoutInflater.from(c);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = null;

            flag = false;
// todo 현재 위치
            GPSTracker gps = new GPSTracker(getApplicationContext());
            if(gps.canGetLocation()){
                flag = true;
                lostLatitude = gps.getLatitude();
                lostLongitude = gps.getLongitude();
            }else{
                gps.showSettingsAlert();
            }
            // todo 도착 위치 - csv파일 분석후 가장 가까운 위경도------------------------------------

            String addressString = "";
            CustomAddress address = new CustomAddress(getApplicationContext());
            addressString = address.findAddress(lostLatitude,lostLongitude);

            if(disaster==null || disaster=="") {
                collection = readPatternCSVLineByLine("outside_shelter.csv", addressString);
                exnum = 6;
                eynum = 5;
            }else{
                if (disaster.contains("지진")) {
                    collection = readPatternCSVLineByLine("outside_shelter.csv", addressString);
                    exnum = 6;
                    eynum = 5;
                } else if (disaster.contains("폭염")) {
                    collection = readPatternCSVLineByLine("heat_wave_shelter.csv", addressString);
                    exnum = 17;
                    eynum = 18;
                } else if (disaster.contains("황사")) {
                    collection = readPatternCSVLineByLine("heat_wave_shelter.csv", addressString);
                    exnum = 17;
                    eynum = 18;
                } else if (disaster.contains("태풍")) {
                    collection = readPatternCSVLineByLine("inside_shelter.csv", addressString);
                    exnum = 6;
                    eynum = 5;
                }
            }
            switch (position) {
                case 0:
                    view = layoutInflater.inflate(layouts[position],null);
                    im1 = (ImageView) view.findViewById(R.id.m1);
                    im2 = (ImageView) view.findViewById(R.id.m2);
                    im3 = (ImageView) view.findViewById(R.id.m3);
                    im4 = (ImageView) view.findViewById(R.id.m4);

                    im1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(StartPage.this, popuptest.class);
                            intent.putExtra("massa","1");
                            startActivity(intent);
                        }
                    });
                    im2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(StartPage.this, popuptest.class);
                            intent.putExtra("massa","2");
                            startActivity(intent);
                        }
                    });

                    im3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(StartPage.this, popuptest.class);
                            intent.putExtra("massa","3");
                            startActivity(intent);
                        }
                    });
                    im4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(StartPage.this, popuptest.class);
                            intent.putExtra("massa","4");
                            startActivity(intent);
                        }
                    });

                    break;
                case 1:
                    view = layoutInflater.inflate(layouts[position],null);
                    view.setBackgroundResource(R.drawable.back2);
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync((OnMapReadyCallback) view.getContext());
                    break;
                case 2:
                    view = layoutInflater.inflate(layouts[position],null);
                    view.setBackgroundResource(R.drawable.back2);

                    mWebView2 = (WebView) view.findViewById(R.id.wb2);
                    WebSettings webSettings2 = mWebView2.getSettings();
                    webSettings2.setJavaScriptEnabled(true);



                    if(flag) {
                        Log.d(TAG,"로케이션 지정 완료");
                        mWebView2.setWebViewClient(new WebViewClient() {
                            @Override
                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                view.loadUrl(url);
                                return true;
                            }
                        });

                        if(disaster==null || disaster==""){
                            mWebView2.loadUrl("http://ebay.auction.co.kr/m/?dir=shop&menu=keyword&q=disaster+kit&sk_idx=1068140&siteID=0&sk=%EC%9E%AC%EB%82%9C%ED%82%B7");
                        }else if(disaster.contains("폭염")){
                            mWebView2.loadUrl("http://ebay.auction.co.kr/?dir=shop&menu=keyword&q=cooling+towel&sk_idx=0&siteID=0&sk=cooling+towel");

                        }else if(disaster.contains("황사")){
                            mWebView2.loadUrl("http://ebay.auction.co.kr/?dir=shop&menu=keyword&q=anti+dust+mask&sk_idx=0&siteID=0&sk=anti+dust+mask");
                        }else if(disaster.contains("태풍")){
                            mWebView2.loadUrl("http://ebay.auction.co.kr/m/?dir=shop&menu=keyword&q=candle&sk_idx=13301&siteID=0&sk=%EC%96%91%EC%B4%88");
                        }else if(disaster.contains("지진")){
                            mWebView2.loadUrl("http://ebay.auction.co.kr/m/?dir=shop&menu=keyword&q=disaster+kit&sk_idx=1068140&siteID=0&sk=%EC%9E%AC%EB%82%9C%ED%82%B7");
                        }
                    }else{
                        Log.d(TAG,"로케이션 지정 불가 새로고치 요망");
                    }

                    break;
            }

            container.addView(view,0);

            return view;
        }


        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }
/*
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }*/
        @Override
        public void destroyItem(View pager, int position, Object view) {

            ((ViewPager)pager).removeView((View)view);

        }
        @Override public void restoreState(Parcelable arg0, ClassLoader arg1) {}

        @Override public Parcelable saveState() { return null; }

        @Override public void startUpdate(View arg0) {}

        @Override public void finishUpdate(View arg0) {}
    }

    @Override
    public void onPause() {
         super.onPause();
            if(mWebView2 != null) {
                mWebView2.clearCache(true);
                mWebView2 = null;
            }
    }

    @Override
    public void onResume() {
        super.onResume();
        viewPager.setCurrentItem(0, false);
    }

    @Override
    public AssetManager getAssets() {
        return getResources().getAssets();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mWebView2 != null) {
            mWebView2.clearCache(true);
            mWebView2 = null;
        }
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        GPSTracker gps = new GPSTracker(getApplicationContext());
        if(gps.canGetLocation()){
            lostLatitude = gps.getLatitude();
            lostLongitude = gps.getLongitude();
        }else{
            gps.showSettingsAlert();
        }

        for (String[] e : collection) {
            if(e[eynum]!="" || e[exnum]!="" || e[eynum]!=null || e[exnum]!=null) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(e[exnum]), Double.parseDouble(e[eynum]))).title(e[2]).visible(true));
                Log.d("testt", e[exnum] + "," + e[eynum] + " / " + e[2]);
            }
        }
        LatLng current = new LatLng(lostLatitude, lostLongitude);
        mMap.addMarker(new MarkerOptions().position(current).title("You are here!").visible(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        Log.d("testt",current.latitude + "," + current.longitude);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 14));
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(
                this, R.raw.mapstyle_grayscale);
        mMap.setMapStyle(style);
        mProgressMid.setVisibility(View.GONE);

    }
}
