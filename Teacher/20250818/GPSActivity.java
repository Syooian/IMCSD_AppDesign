package com.example.application2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Location;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GPSActivity extends AppCompatActivity implements LocationListener {

    private TextView textview_gps_information;
    private Button button_set_gps, button_start_gps, button_show_map;
    private LocationManager lm;
    private Location currentLocation;
    private String bestProvider;
    private static final int PERMISSION_REQUEST_GPS = 1201;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gpsactivity);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        textview_gps_information = findViewById(R.id.textview_gps_information);
        button_set_gps = findViewById(R.id.button_set_gps);
        button_start_gps = findViewById(R.id.button_start_gps);
        button_show_map = findViewById(R.id.button_show_map);

        lm = (LocationManager)getSystemService(LOCATION_SERVICE);
        checkGPSProviderStatus(lm);
        checkSDKVersion();

        button_set_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        });

        button_start_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double lat, lng;
                String strOutput;
                int minFlashTime = 1000;
                float minFlashDistence = 1.0f;

                bestProvider = "gps";

                try {
                    if (bestProvider != null) {
                        lm.requestLocationUpdates(bestProvider, minFlashTime, minFlashDistence, GPSActivity.this);
                        currentLocation = lm.getLastKnownLocation(bestProvider);

                        if(currentLocation != null){
                            lat = currentLocation.getLatitude();
                            lng = currentLocation.getLongitude();
                            strOutput =  "GPS資訊 : 啟動 GPS 更新功能成功!!  \n定位資料提供者 : " + bestProvider + "\n目前座標，緯度 : " +  lat + " - 經度 : " + lng;
                        } else {
                            strOutput =  "GPS資訊 : 等待 GPS 座標更新中...   目前座標 = null";
                        }
                    } else {
                        strOutput =  "GPS資訊 : 啟動 GPS 更新功能失敗...   定位資料提供者 = null";
                    }

                    textview_gps_information.setText(strOutput);
                } catch (SecurityException ex) {
                    ex.printStackTrace();
                }
            }
        });

        button_show_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double lat, lng;
                String label = "目前位置";
                String geoString, queryString, uriString;

                if (currentLocation != null) {
                    lat = currentLocation.getLatitude();
                    lng = currentLocation.getLongitude();
                    geoString = "geo:" + lat + "," + lng;
                    queryString = lat + "," + lng + "(" + label + ")";
                    uriString = Uri.encode(queryString);
                    geoString = geoString + "?q=" + uriString;

                    Intent geoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoString));
                    startActivity(geoIntent);
                } else {
                    AlertDialog.Builder b = new AlertDialog.Builder(GPSActivity.this);
                    b.setTitle("GPS 地圖功能");
                    b.setMessage("目前GPS自動更新功能尚未啟用，請先按鈕啟用GPS更新，否則無法正確顯示地圖座標...");
                    b.setPositiveButton("確定", null);
                    b.create();
                    b.show();
                }
            }
        });

    }

    public void checkGPSProviderStatus(LocationManager location_manager) {
        if (location_manager.isProviderEnabled(LocationManager.GPS_PROVIDER) == false) {
            AlertDialog.Builder builder = new AlertDialog.Builder(GPSActivity.this);
            builder.setTitle("GPS 硬體功能設定");
            builder.setMessage("GPS 硬體裝置尚未啟用 !! \n請先啟用 GPS 裝置，否則無法接收位置資訊... ");
            builder.setPositiveButton("已充分了解", null);
            builder.create();
            builder.show();
        } else {
            Toast.makeText(GPSActivity.this, "GPS 硬體裝置已啟用", Toast.LENGTH_LONG);
        }
    }

    public void checkSDKVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_GPS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        String  strOutput;

        if (requestCode == PERMISSION_REQUEST_GPS) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    strOutput = "GPS資訊 : 要求 GPS 裝置使用權限成功 !!";
                } else {
                    strOutput = "GPS資訊 : 要求 GPS 裝置使用權限失敗.... ";
                }
            } else {
                strOutput = "GPS資訊 : 系統錯誤!!  未成功回傳GPS裝置授權要求.....  ";
            }
            textview_gps_information.setText(strOutput);
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        double lat, lng;
        String strOutput;

        if (location != null) {
            currentLocation = location;
            lat = currentLocation.getLatitude();
            lng = currentLocation.getLongitude();
            strOutput = "GPS資訊 : 座標更新成功!! \n定位資料提供者 : " + bestProvider + "\n目前座標，緯度 : " + lat + "- 經度 : " + lng;
        } else {
            strOutput = "GPS資訊 : 座標更新失敗... onLocationChanged -> location  為 null";
        }

        textview_gps_information.setText(strOutput);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (lm != null){
            lm.removeUpdates(GPSActivity.this);
            Log.d("GPSActivity : ", "GPS訊號自動更新 - 功能已關閉！！");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        int minFlashTime = 1000;
        float minFlashDistence = 1.0f;
        String strOutput;

        if ((bestProvider == null) ||bestProvider.equals("")) {
            bestProvider = "gps";
        }

        try {
            if (bestProvider != null) {
                lm.requestLocationUpdates(bestProvider, minFlashTime, minFlashDistence, GPSActivity.this);
                strOutput = "GPS訊號自動更新 - 啟動 GPS 更新功能成功!! \n定位資料提供者 : " + bestProvider;
            } else {
                strOutput = "GPS訊號自動更新 - 啟動 GPS 更新功能失敗.... \n定位資料提供者 : bestProvider = bull";
            }
            Log.d("GPSActivity : ", strOutput);
        } catch (SecurityException ex) {
            strOutput = "GPS訊號自動更新 - 啟動 GPS 更新功能失敗.... \n發生 SecurityException，錯誤訊息 : " + ex.getMessage();
            Log.d("GPSActivity : ", strOutput);
        }

    }
}