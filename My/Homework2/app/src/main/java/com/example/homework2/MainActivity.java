package com.example.homework2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
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

        //加入清除按鈕的Click事件
        findViewById(R.id.Btn_CleanData).setOnClickListener(V -> {
            CleanDataList();
            Toast.makeText(this, "資料已清除", Toast.LENGTH_SHORT).show();
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
        CleanDataList();

        new Thread(() -> {
            final String[] ToastMessage = new String[1];
            //變數在 try 區塊和 catch 區塊都可能被賦值，Java 不允許在 lambda 外部這樣重複賦值。
            //建議改用陣列或將 ToastMessage 宣告在 try-catch 外並只賦值一次。

            try {
                URL URL = new URL(API_URL);
                HttpURLConnection Connection = (HttpURLConnection) URL.openConnection();
                Connection.setRequestMethod("GET");
                Connection.setConnectTimeout(5000); // 設定連線超時時間為 5 秒
                Connection.setReadTimeout(10000); // 設定讀取超時時間為 10 秒
                Connection.connect();

                int ResponseCode = Connection.getResponseCode();
                if (ResponseCode == HttpURLConnection.HTTP_OK) {
                    int Total = Connection.getContentLength();
                    BufferedReader Reader = new BufferedReader(new InputStreamReader(Connection.getInputStream()));
                    StringBuffer Buffer = LoadString(Reader, Total);

                    if (Buffer.length() == 0) {
                        ToastMessage[0] = "取資料失敗";
                    } else {
                        ToastMessage[0] = "取資料成功";

                        ShowData(Buffer.toString());
                    }

                    //SwipeRefresh.setRefreshing(false); // 停止刷新動畫
                } else {
                    Log.e(TAG, "取資料失敗 Code : " + ResponseCode);
                    ToastMessage[0] = LoadLocalData();
                }

                if (Connection != null) {
                    Log.v(TAG, "斷開連線");
                    Connection.disconnect();
                }
            } catch (UnknownHostException E) {
                Log.e(TAG, "網路錯誤 : " + E.toString());

                ToastMessage[0] = LoadLocalData();

                //Sleep(1000);//模擬延遲
            } catch (Exception E) {
                Log.e(TAG, "取資料失敗 : " + E.toString());
                ToastMessage[0] = LoadLocalData();
            }

            runOnUiThread(() -> {
                ShowProgressBar(false, null);
                Toast.makeText(this, ToastMessage[0], Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

    //讀取本地暫存資料
    String LoadLocalData() {
        Sleep(1000);//模擬延遲

        runOnUiThread(() -> Toast.makeText(this, "取資料失敗，將使用本地暫存資料，並請檢查網路連線。", Toast.LENGTH_SHORT).show());

        Sleep(1000);//模擬延遲

        try {
            InputStream IS = getAssets().open("LocalData.txt");
            BufferedReader Reader = new BufferedReader(new InputStreamReader(IS, "UTF-8"));

            //本地讀取用StringBuilder即可，但為求統一方法所以使用StringBuffer
            StringBuffer Buffer = LoadString(Reader, 0);

            Sleep(1000);

            if (Buffer.length() == 0) {
                return "讀取本地暫存資料失敗";
            } else {
                ShowData(Buffer.toString());

                return "讀取本地暫存資料完成";
            }
        } catch (Exception E) {

            Sleep(1500);//模擬延遲

            Log.e(TAG, "LoadLocalData E : " + E.toString());
            return "讀取本地暫存資料失敗";
        }
    }

    StringBuffer LoadString(BufferedReader Reader, int Total) {
        StringBuffer Buffer = new StringBuffer();
        String Line;

        int Read = 0;

        Log.d(TAG, "Total : " + Total);

        try {
            while ((Line = Reader.readLine()) != null) {
                Buffer.append(Line);
                Read += Line.length();

                if (Total > 0) {
                    float Progress = (Read * 100f) / Total;
                    runOnUiThread(() -> ShowProgressBar(true, Progress));
                }

                Sleep(1000);//模擬延遲
            }

            Reader.close();
        } catch (Exception E) {
            Log.e(TAG, "LoadString Error : " + E.toString());
        }

        runOnUiThread(() -> ShowProgressBar(true, 100f));
        Sleep(1000);//模擬延遲

        //可能需要注意如果出現Exception時 Buffer 可能會是空的
        return Buffer;
    }

    //模擬延遲
    void Sleep(long Millis) {
        try {
            Thread.sleep(Millis); // 模擬延遲，實際應用中可根據需要調整
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    //清除畫面上的資料
    void CleanDataList() {
        DataList.setAdapter(null);
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

/*class Station {
    public int seq;
    public String 車站編號;
    public String 車站中文名稱;
    public String 車站英文名稱;
    public String 車站緯度;
    public String 車站經度;
}*/