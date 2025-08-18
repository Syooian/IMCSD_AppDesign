package com.example.gps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LocationListener {

    LocationManager LM;

    final String TAG = "com.example.gps";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LM = (LocationManager) getSystemService(LOCATION_SERVICE);
        /*if (CheckGPSProviderStatus()) {
            Toast.makeText(this, "GPS已啟用", Toast.LENGTH_LONG);
        }*/

        //Android6.0以上需動態請求權限
        //CheckPermission();
    }

    //檢查GPS狀態
    Boolean CheckGPSProviderStatus() {
        Boolean Result;

        if (!LM.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder Builder = new AlertDialog.Builder(this);
            Builder.setTitle("GPS設定");
            Builder.setMessage("GPS未啟用，請先啟動GPS功能。");
            Builder.setPositiveButton("設定", (dialog, which) -> {
                // 這裡可以添加跳轉到GPS設定的代碼
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse(("Package:" + getPackageName())));
                startActivity(intent);

                //CheckPermission();
            });
            Builder.create();
            Builder.show();

            Result = false;
        } else {
            Result = true;
        }

        Log.v(TAG, "檢查GPS狀態 Result: " + Result);

        return Result;
    }

    void CheckPermission() {
        ArrayList<String> Permissions = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (Permissions.size() > 0) {
            Boolean CallIntent = false;

            for (int a = 0; a < Permissions.size(); a++) {
                Log.v(TAG, "需要的權限: " + Permissions.get(a));

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Permissions.get(a))) {
                    // 用戶之前拒絕但沒勾「不再詢問」

                    Log.v(TAG, "CP " + (a + 1) + "-1");
                    ActivityCompat.requestPermissions(this, Permissions.toArray(new String[a]), 1);
                } else {
                    // 用戶勾選了「不再詢問」

                    AlertDialog.Builder Builder = new AlertDialog.Builder(this);
                    Builder.setTitle("GPS設定");
                    Builder.setMessage("GPS未啟用，請先啟動GPS功能。");
                    Builder.setPositiveButton("設定", (dialog, which) -> {
                        // 這裡可以添加跳轉到GPS設定的代碼
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse(("package:" + getPackageName())));
                        startActivity(intent);

                        //CheckPermission();
                    });
                    Builder.create();
                    Builder.show();

                    break;
                }
            }
        } else {
            Log.v(TAG, "所有GPS權限皆已許可");
            Toast.makeText(this, "所有GPS權限皆已許可", Toast.LENGTH_LONG).show();
        }

        /*if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "CP 1");

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.v(TAG, "CP 1-1");
                // 用戶之前拒絕但沒勾「不再詢問」
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                Log.v(TAG, "CP 1-2");
                // 用戶勾選了「不再詢問」
                AlertDialog.Builder Builder = new AlertDialog.Builder(this);
                Builder.setTitle("GPS設定");
                Builder.setMessage("GPS未啟用，請先啟動GPS功能。");
                Builder.setPositiveButton("設定", (dialog, which) -> {
                    // 這裡可以添加跳轉到GPS設定的代碼
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse(("package:" + getPackageName())));
                    startActivity(intent);

                    //CheckPermission();
                });
                Builder.create();
                Builder.show();
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "CP 2");

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Log.v(TAG, "CP 2-1");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            } else {
                Log.v(TAG, "CP 2-2");
                // 用戶勾選了「不再詢問」
                AlertDialog.Builder Builder = new AlertDialog.Builder(this);
                Builder.setTitle("GPS設定");
                Builder.setMessage("GPS未啟用，請先啟動GPS功能。");
                Builder.setPositiveButton("設定", (dialog, which) -> {
                    // 這裡可以添加跳轉到GPS設定的代碼
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse(("package:" + getPackageName())));
                    startActivity(intent);

                    //CheckPermission();
                });
                Builder.create();
                Builder.show();
            }
        }*/
    }

    public void SetGPS(View view) {
        //if (CheckGPSProviderStatus()) {
            /*Location Location = LM.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            String Info;
            if (Location != null) {
                Info = "緯度: " + Location.getLatitude() + "\n經度: " + Location.getLongitude();
            } else {
                Info = "無法獲取GPS資訊";
            }

            SetGPSInfo(Info);*/
        //} else {

        CheckPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int a = 0; a < permissions.length; a++) {
                switch (grantResults[a]){
                    case PackageManager.PERMISSION_GRANTED:
                        Log.v(TAG, "權限 " + permissions[a] + " 已授予");
                        //Toast.makeText(this, "權限已授予: " + permissions[a], Toast.LENGTH_SHORT).show();
                        break;
                    case PackageManager.PERMISSION_DENIED:
                        Log.v(TAG, "權限 " + permissions[a] + " 被拒絕");
                        //Toast.makeText(this, "權限被拒絕: " + permissions[a], Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            /*if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "權限已授予", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "權限被拒絕", Toast.LENGTH_SHORT).show();

                //CheckGPSProviderStatus();
            }*/
        }
    }

    //當GPS資訊變更時
    @Override
    public void onLocationChanged(Location Location) {
        double lat, lng;

        if (Location != null) {
            lat = Location.getLatitude();
            lng = Location.getLongitude();

            SetGPSInfo("緯度: " + lat + "\n經度: " + lng);
        } else {

        }
    }

    //設置GPS資訊顯示
    public void SetGPSInfo(String Info) {
        var TextView = (TextView) findViewById(R.id.Txt_GPSInfo);
        TextView.setText(Info);
    }

    //執行中
    @Override
    protected void onResume() {
        super.onResume();

        try {
            //抓取GPS資訊
            Log.v(TAG,"抓取GPS");
            LM.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
        } catch (SecurityException E) {
            Log.e(TAG, "onResume SecurityException : " + E.getMessage());

            SetGPSInfo("需要GPS權限");
        } catch (Exception E) {
            Log.e(TAG, "onResume Exception : " + E.getMessage());
        }
    }

    //暫停
    @Override
    protected void onPause() {
        super.onPause();

        try {
            //停止抓取GPS資訊
            Log.v(TAG,"暫停GPS");
            LM.removeUpdates(this);
        } catch (SecurityException E) {
            Log.e(TAG, "onPause SecurityException : " + E.getMessage());
        } catch (Exception E) {
            Log.e(TAG, "onPause Exception : " + E.getMessage());
        }
    }
}