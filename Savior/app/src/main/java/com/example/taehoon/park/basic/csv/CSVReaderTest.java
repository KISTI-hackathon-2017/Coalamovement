package com.example.taehoon.park.basic.csv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.taehoon.park.R;
import com.opencsv.CSVReader;

import java.io.InputStreamReader;
import java.util.ArrayList;

public class CSVReaderTest extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csvreader_test);

        ArrayList<String[]> collection;
        collection= readPatternCSVLineByLine("outside_shelter.csv","서귀포시");

        for(String[] e : collection){
            Log.d("testt",e[0]+","+e[1]+","+e[2]+","+e[3]+","+e[4]+","+e[5]+","+e[6]);
        }
        // todo *** 5:경 6:위

    }

    public ArrayList<String[]> readPatternCSVLineByLine(String filename,String sublocality){
        ArrayList<String[]> rows = new ArrayList<String[]>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(getAssets().open(filename),"euc-kr"));
            String [] nextLine;;
            while ((nextLine = reader.readNext()) != null) {
                if(nextLine[1].equals(sublocality)) {
                    rows.add(nextLine);
                }
            }
        } catch (Exception e) {
            Log.e("File", "Error while read Pattern CSV file : " + e);
        }
        return rows;
    }
}
