package com.example.homework2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
        Loading = findViewById(R.id.Loading);
        DataList = findViewById(R.id.DataList);

        SwipeRefresh = findViewById(R.id.SwipeRefresh);
        SwipeRefresh.setOnRefreshListener(() -> {
            Log.d(TAG, "下滑更新");

            GetData(null);
            SwipeRefresh.setRefreshing(false); // 停止刷新動畫
        });
    }

    //進度條
    ProgressBar ProgressBar;
    //讀取圈
    ProgressBar Loading;
    //資料顯示
    ListView DataList;
    //下滑更新
    SwipeRefreshLayout SwipeRefresh;

    //進度條開關, 顯示進度
    void ShowProgressBar(Boolean OnOff, Float Value) {
        //物件開關
        ProgressBar.setVisibility(OnOff ? View.VISIBLE : View.INVISIBLE);
        //讀取圈
        Loading.setVisibility(OnOff ? View.VISIBLE : View.INVISIBLE);

        if (Value != null) {
            Log.v(TAG, "SetProgress : " + Value);

            ProgressBar.setProgress(Value.intValue(), true);
        }
    }

    //取WebAPI資料
    public void GetData(View View) {
        Toast.makeText(this, "正在取得資料...", Toast.LENGTH_SHORT).show();

        ShowProgressBar(true, 0f);

        //清空已顯示的資料
        DataList.setAdapter(null);

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

                    Log.d(TAG, "Total : " + Total);

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

                    runOnUiThread(() -> Toast.makeText(this, "取資料成功", Toast.LENGTH_SHORT).show());

                    ShowData(Builder.toString());

                    //SwipeRefresh.setRefreshing(false); // 停止刷新動畫
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


    void ShowData(String Data) {
        //JsonObjectRequest Request=new JsonObjectRequest()

        //JsonReader Reader=new JsonReader(Data);

        try {
            JSONObject Json = new JSONObject(Data);

            ArrayList<HashMap<String, String>> ArrayList = new ArrayList<>();

            JSONArray DataArray = Json.getJSONArray("data");

            for (int a = 0; a < DataArray.length(); a++) {
                JSONObject Item = DataArray.getJSONObject(a);
                //Log.v(TAG, Item.toString());

                //將資料放入Dic
                HashMap<String, String> Station = new HashMap<String, String>();
                Station.put("車站編號", Item.optString("車站編號"));
                Station.put("車站中文名稱", Item.optString("車站中文名稱"));
                Station.put("車站緯度", Item.optString("車站緯度"));
                Station.put("車站經度", Item.optString("車站經度"));

                //將Dic放入ArrayList
                ArrayList.add(Station);
            }

            runOnUiThread(() -> {
                StationAdapter Adapter = new StationAdapter(this, ArrayList);

                DataList.setAdapter(Adapter);

                //設定點下物件後開啟地圖
                DataList.setOnItemClickListener((Parent, View, Position, ID) -> {
                    HashMap<String, String> Station = ArrayList.get(Position);
                    String Title = Station.get("車站中文名稱");
                    double Lat = Double.parseDouble(Station.get("車站緯度"));
                    double Lng = Double.parseDouble(Station.get("車站經度"));
                    ShowMap(Title, Lat, Lng);
                });
            });
        } catch (Exception E) {
            Log.e(TAG, "ShowDataError : " + E.getMessage());
        }
    }

    void ShowMap(String Title, double Lat, double Lng) {
        try {
            var GeoString = "geo:" + Lat + "," + Lng;
            var QueryString = Lat + "," + Lng + "(" + Title + ")";
            var UriString = Uri.encode(QueryString);
            GeoString += "?q=" + UriString;

            //開啟地圖
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(GeoString)));
        } catch (Exception E) {
            Log.e(TAG, "ShowMapError : " + E.getMessage());
            Toast.makeText(this, "顯示地圖失敗", Toast.LENGTH_SHORT).show();
        }
    }


    class StationAdapter extends ArrayAdapter<HashMap<String, String>> {
        public StationAdapter(Context Context, ArrayList<HashMap<String, String>> Stations) {
            super(Context, android.R.layout.simple_list_item_1, Stations);
        }

        @Override
        public View getView(int Position, View ConvertView, ViewGroup Parent) {
            if (ConvertView == null) {
                ConvertView = View.inflate(getContext(), android.R.layout.simple_list_item_1, null);
            }

            HashMap<String, String> Station = getItem(Position);
            String Text = Station.get("車站編號") + "\n" +
                    Station.get("車站中文名稱") + "\n" +
                    Station.get("車站緯度") + ":" + Station.get("車站經度");
            ((android.widget.TextView) ConvertView.findViewById(android.R.id.text1)).setText(Text);
            return ConvertView;
        }
    }
}

class Station {
    public int seq;
    public String 車站編號;
    public String 車站中文名稱;
    public String 車站英文名稱;
    public String 車站緯度;
    public String 車站經度;
}