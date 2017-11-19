package com.example.taehoon.park.basic.json;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.taehoon.park.R;
import com.example.taehoon.park.basic.http.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonTest extends AppCompatActivity {
    private static final String TAG= "JsonTest";

    public String fixurl = "http://openapi.meis.go.kr/rest/datalist?id=WemoData&sdate=2012-01-01&edate=2012-01-01&sta_cde=213";
    public String JSON_STRING;
    public Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_test);
        b1 = (Button) findViewById(R.id.b1);
        try {
            final HttpUtil task;
            task = new HttpUtil(fixurl,"GET");
            task.execute();

            Handler hd = new Handler();
            hd.postDelayed(new Runnable() {
                @Override
                public void run() {
                    JSON_STRING = task.getResult();
                    Log.d(TAG,"JSONObject : " + JSON_STRING);
                }
            }, 1500);

        } catch (Exception e) {
            e.printStackTrace();
        }



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONArray jarray = new JSONArray(JSON_STRING);
                    for(int i=0; i < jarray.length(); i++){
                        JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출
                        // todo 각 오브젝트에서 필요한 요소를 객체로 저장하여 사용하면 됩니다.
                        //todo http://bitsoul.tistory.com/4
                        Log.d(TAG,"JSONObject : " + jObject);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
