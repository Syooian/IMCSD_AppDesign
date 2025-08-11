package com.example.intent04;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button button_browser, button_dial_phone, button_start_map;
    EditText edittext_url, edittext_phone_number, edittext_gps_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });


        button_browser = findViewById(R.id.button_browser);
        button_dial_phone = findViewById(R.id.button_dial_phone);
        button_start_map = findViewById(R.id.button_start_map);
        edittext_url = findViewById(R.id.edittext_url);
        edittext_phone_number = findViewById(R.id.edittext_phone_number);
        edittext_gps_address = findViewById(R.id.edittext_gps_address);

        button_browser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUrl = edittext_url.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(strUrl));
                startActivity(i);
            }
        });

        button_dial_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strPhoneNumber = edittext_phone_number.getText().toString();
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse(strPhoneNumber));
                startActivity(i);
            }
        });

        button_start_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strGPSAddress = edittext_gps_address.getText().toString();
                Intent i = new Intent();
                i.setData(Uri.parse(strGPSAddress));
                i.setAction(Intent.ACTION_VIEW);
                startActivity(i);
            }
        });

    }
}