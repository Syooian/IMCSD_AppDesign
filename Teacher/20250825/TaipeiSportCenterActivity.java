package com.example.application2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TaipeiSportCenterActivity extends AppCompatActivity {

    TextView taipei_sport_center_textview_title;
    Button taipei_sport_center_button_get_data, taipei_sport_center_button_clear_data;
    ListView taipei_sport_center_listview_apidata;
    ArrayList<String> taipei_sport_center_arrayListOfString;
    ArrayAdapter<String> taipei_sport_center_arrayAdapter;

    String strAPI_URL = "https://data.taipei/api/v1/dataset/e7c46724-3517-4ce5-844f-5a4404897b7d?scope=resourceAquire";
    String strTaipeiSportCenter = "台北市運動中心資料";
    String strCenterName, strCenterAddress, strLat, strLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_taipei_sport_center);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        taipei_sport_center_textview_title = findViewById(R.id.taipei_sport_center_textview_title);
        taipei_sport_center_button_clear_data = findViewById(R.id.taipei_sport_center_button_clear_data);
        taipei_sport_center_button_get_data = findViewById(R.id.taipei_sport_center_button_get_data);
        taipei_sport_center_listview_apidata = findViewById(R.id.taipei_sport_center_listview_apidata);

        taipei_sport_center_arrayListOfString = new ArrayList<String>();

        taipei_sport_center_button_clear_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((taipei_sport_center_arrayListOfString != null) && (taipei_sport_center_arrayAdapter != null)) {
                    taipei_sport_center_arrayListOfString.clear();
                    taipei_sport_center_arrayAdapter.notifyDataSetChanged();
                    taipei_sport_center_textview_title.setText(strTaipeiSportCenter + "\nAPI資料已清空....");
                }
            }
        });

        taipei_sport_center_button_get_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, strAPI_URL, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            int i;

                            try {
                                JSONObject jsonObject_result = response.getJSONObject("result");
                                JSONArray jsonArray_results = jsonObject_result.getJSONArray("results");

                                for (i = 0; i < jsonArray_results.length(); i++) {
                                    JSONObject jsonObject_sport_center = jsonArray_results.getJSONObject(i);
                                    strCenterName = jsonObject_sport_center.getString("名稱");
                                    strCenterAddress = jsonObject_sport_center.getString("地址");
                                    strLat = jsonObject_sport_center.getString("緯度");
                                    strLng = jsonObject_sport_center.getString("經度");

                                    taipei_sport_center_arrayListOfString.add((i+1) + ". " + strCenterName + "-\n" + strCenterAddress + "\n座標 : " +
                                                                              strLat + "," + strLng);
                                }
                                taipei_sport_center_arrayAdapter = new ArrayAdapter<String>(TaipeiSportCenterActivity.this,
                                        android.R.layout.simple_list_item_1, taipei_sport_center_arrayListOfString);

                                taipei_sport_center_listview_apidata.setAdapter(taipei_sport_center_arrayAdapter);
                                taipei_sport_center_textview_title.setText(strTaipeiSportCenter + "\nAPI資料讀取完成，解析成功!!");

                                taipei_sport_center_listview_apidata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        String taipei_sport_center_ItemString = taipei_sport_center_arrayListOfString.get(position);
                                        int intStartOfGeo = taipei_sport_center_ItemString.indexOf("座標");
                                        String strGeoPosition = taipei_sport_center_ItemString.substring(intStartOfGeo + 4);
                                        String strArrayLatLng[] = strGeoPosition.split(",");
                                        String strLabel = strArrayLatLng[0] + "," + strArrayLatLng[1] + "(運動中心位置)";
                                        String strQuery = Uri.decode(strLabel);
                                        Intent intentGeo = new Intent(Intent.ACTION_VIEW,  Uri.parse ("geo:" + strGeoPosition +
                                                                      "?q=" + strQuery + "z = 32"));

                                        startActivity(intentGeo);
                                    }
                                });

                                } catch (JSONException ex) {

                                taipei_sport_center_textview_title.setText(strTaipeiSportCenter + "\nAPI資料解析失敗，錯誤訊息 :\n"+ ex.getMessage());

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            taipei_sport_center_textview_title.setText(strTaipeiSportCenter + "\n向伺服器取回資料時發生VolleyError，錯誤訊息 :\n" +
                                                                       error.getMessage());
                        }
                    });

                    Volley.newRequestQueue(TaipeiSportCenterActivity.this).add(request);
                    taipei_sport_center_textview_title.setText(strTaipeiSportCenter + "\nAPI資料正在讀取中......");

                } catch (Exception ex) {
                    taipei_sport_center_textview_title.setText(strTaipeiSportCenter + "\n嘗試向伺服器發送API請求失敗，錯誤訊息 :\n" + ex.getMessage());
                }
            }
        });

    }
}