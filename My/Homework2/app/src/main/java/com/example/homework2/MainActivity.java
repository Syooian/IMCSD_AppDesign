package com.example.homework2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    final String TAG = "WebAPI";
    final String API_URL = "https://api.kcg.gov.tw/api/service/Get/4278fc6a-c3ea-4192-8ce0-40f00cdb40dd";

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

        ProgressBar = findViewById(R.id.ProgressBar);
    }

    //進度條
    ProgressBar ProgressBar;

    //進度條開關, 顯示進度
    void ShowProgressBar(Boolean OnOff, Float Value) {
        //物件開關
        ProgressBar.setVisibility(OnOff ? View.VISIBLE : View.INVISIBLE);

        if (Value != null) {
            Log.v(TAG,"SetProgress : "+Value);

            ProgressBar.setProgress(Value.intValue(), true);
        }
    }

    //取WebAPI資料
    public void GetData(View View) {
        ShowProgressBar(true, 0f);

        new Thread(() -> {
            try {
                URL URL = new URL(API_URL);
                HttpURLConnection Connection = (HttpURLConnection) URL.openConnection();
                Connection.setRequestMethod("GET");
                Connection.connect();

                int ResponseCode = Connection.getResponseCode();
                if (ResponseCode == HttpURLConnection.HTTP_OK) {
                    int Total = Connection.getContentLength();
                    BufferedReader Reader = new BufferedReader(new InputStreamReader(Connection.getInputStream()));
                    StringBuffer Builder = new StringBuffer();
                    String Line;
                    int Read = 0;

                    Log.d(TAG,"Total : "+Total);

                    while ((Line = Reader.readLine()) != null) {
                        Builder.append(Line);
                        Read += Line.length();

                        if (Total > 0) {
                            float Progress = (Read * 100f) / Total;
                            runOnUiThread(() -> ShowProgressBar(true, Progress));
                        }

                        Thread.sleep(1000); // 模擬延遲，實際應用中可根據需要調整
                    }
                    Reader.close();
                    Log.v(TAG, "取資料成功 : " + Builder.toString());
                } else {
                    Log.e(TAG, "取資料失敗 Code : " + ResponseCode);
                }
            } catch (Exception E) {
                Log.e(TAG, "取資料失敗 : " + E.toString());
                Toast.makeText(this, "取資料失敗", Toast.LENGTH_SHORT).show();
            }

            runOnUiThread(() -> ShowProgressBar(false, null));
        }).start();
    }
}