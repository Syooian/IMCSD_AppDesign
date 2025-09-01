package com.example.application2;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GPSDecodeActivity extends AppCompatActivity {

    private EditText edittext_latitude, edittext_longitude;
    private TextView textview_result_address;
    private Button button_query_address;
    private Geocoder geo;
    private List<Address> list_address;
    private double lat, lng;

    private static final int MAX_RESULT_LENGTH = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gpsdecode);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        edittext_latitude = findViewById(R.id.edittext_latitude);
        edittext_longitude = findViewById(R.id.edittext_longitude);
        textview_result_address = findViewById(R.id.textview_result_address);
        button_query_address = findViewById(R.id.button_query_address);

        geo = new Geocoder(GPSDecodeActivity.this, Locale.TAIWAN);

        button_query_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strOutput = "";
                int i, j;

                lat = Double.parseDouble(edittext_latitude.getText().toString());
                lng = Double.parseDouble(edittext_longitude.getText().toString());

                try {
                    list_address = geo.getFromLocation(lat, lng, MAX_RESULT_LENGTH);

                    if(list_address != null) {
                        if(!(list_address.isEmpty())){
                            for(i = 0; i < list_address.size(); i++) {
                                Address tempAddress = list_address.get(i);
                                strOutput += (i+1) + " : ";

                                for(j = 0; j <= tempAddress.getMaxAddressLineIndex(); j++){
                                    strOutput += tempAddress.getAddressLine(j) + "，";
                                }

                                strOutput += "\n";
                            }
                        } else {
                            strOutput = "查詢結果 : \n回傳的 AddressList 內容為空，輸入的 GPS 座標位置沒有符合的地址回傳 !!";
                        }

                        textview_result_address.setText(strOutput);
                    } else {
                        textview_result_address.setText("AddressList = null，getFromLocationName() 函數呼叫失敗");
                    }

                } catch (IOException ex) {
                    textview_result_address.setText("位置座標查詢 - 發生例外狀況 : " + ex.getMessage());
                }

            }
        });
    }
}