package com.example.taehoon.park.google.LocationSearch.locationsearch;

import android.content.Context;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by taehoon on 2016-08-05.
 */
public class CustomAddress {
    private Context context;

    public CustomAddress(Context context) {
        this.context = context;
    }

    public String findAddress(double lat, double lng) {
        StringBuffer bf = new StringBuffer();
        Geocoder geocoder = new Geocoder(context, Locale.KOREA);
        List<android.location.Address> address;
        String currentLocationAddress;
        try {
            if (geocoder != null) {
                // 세번째 인수는 최대결과값인데 하나만 리턴받도록 설정했다
                address = geocoder.getFromLocation(lat, lng, 2);
                // 설정한 데이터로 주소가 리턴된 데이터가 있으면
                if (address != null && address.size() > 0) {
                    // 주소
                    currentLocationAddress = address.get(0).getAdminArea();
                    Log.d("testt", address.get(1)+"");
                    if(address.get(0).getSubLocality()!=null) {
                        Log.d("testt", address.get(0).getSubLocality());
                    }
                    // 전송할 주소 데이터 (위도/경도 포함 편집)
                    bf.append(currentLocationAddress);
                    /*.append("\n(");
                    bf.append(lat).append(",");
                    bf.append(lng).append(")");*/
                }
            }
        } catch (IOException e) {
            Toast.makeText(context, "주소취득 실패" , Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return bf.toString();
    }

}
