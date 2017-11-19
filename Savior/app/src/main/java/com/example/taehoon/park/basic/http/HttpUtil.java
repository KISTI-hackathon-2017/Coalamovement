package com.example.taehoon.park.basic.http;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by taehoon on 2017-11-13.
 *
 * 실행문
 * BackgroundTask task;
 * task = new BackgroundTask(url,method);
   task.execute();
 */

//--- Todo 권한체크 - <uses-permission android:name="android.permission.INTERNET" />

public class HttpUtil extends AsyncTask<String, Integer, Integer> {
    private static final String TAG= "HttpRequestTest1";

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    private String x = "", y = "";
    String address="";

    public String getResult() {
        return result;
    }

    String result="Result nothing";
    String method = "GET";

    public HttpUtil(String Url,String Mat){
        address = Url;
        method = Mat;
    }
    protected void onPreExecute() {
        Log.d(TAG,"onPreExecute : ");
    }

    @Override
    protected Integer doInBackground(String... arg0) {  // 실행결과
        // TODO Auto-generated method stub
        result = request(address);
       // Log.d(TAG,"doInBackground : " + result);
        return null;
    }

    protected void onPostExecute(Integer a) {
        //Log.d(TAG,"onPostExecute : " + result);
    }

    private String request(String urlStr) {
        StringBuilder output = new StringBuilder();
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            if (conn != null) {
                conn.setConnectTimeout(10000);
                conn.setRequestMethod(method);
                conn.setRequestProperty("Content-type", "application/json");
                conn.setInstanceFollowRedirects(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                int resCode = conn.getResponseCode();
                Log.d(TAG,"conn.getResponseCode() : " + resCode);
                Log.d(TAG,"conn.getHeaderField() : " + conn.getHeaderFields());

                boolean redirect = false;

                if (resCode != HttpURLConnection.HTTP_OK) {
                    if (resCode == HttpURLConnection.HTTP_MOVED_TEMP
                            || resCode == HttpURLConnection.HTTP_MOVED_PERM
                            || resCode == HttpURLConnection.HTTP_SEE_OTHER)
                        redirect = true;
                }
                if (redirect) {
                    String newUrl = conn.getHeaderField("Location");
                    Log.d(TAG,"newUrl() : " + newUrl);

                    newUrl = newUrl.replaceAll("[^-?0-9]+"," ");
                    ArrayList<String> list = new ArrayList<>(Arrays.asList(newUrl.trim().split(" ")));

                    x = list.get(1);
                    y= list.get(3);
                    Log.d(TAG,"newUrl() : " + list);

                }else{
                    Log.d(TAG,"newUrl() : 리다이렉트 아님");
                }


                BufferedReader reader;
                if(resCode >= 200 && resCode <= 300) {
                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream())) ;
                    String line = null;
                    while(true) {
                        line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                        output.append(line + "\n");
                    }

                    reader.close();
                    conn.disconnect();
                }
            }
        } catch(Exception ex) {
            Log.e(TAG, "Exception in processing response.", ex);
            ex.printStackTrace();
        }
        return output.toString();
    }

}
