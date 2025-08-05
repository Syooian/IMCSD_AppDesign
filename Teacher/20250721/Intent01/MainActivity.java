package com.example.intent01;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView textview_main;
    Button button_start_activity_two, button_start_activity_three, button_set_visibility;
    Group view_group_1;

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

        textview_main = findViewById(R.id.textview_main);

        button_start_activity_two = findViewById(R.id.button_start_activity_two);
        button_start_activity_three = findViewById(R.id.button_start_activity_three);
        button_set_visibility = findViewById(R.id.button_set_visibility);

        view_group_1 = findViewById(R.id.view_group_1);

        button_start_activity_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, Activity_Two.class);
                Intent intent_01;
                intent_01 = new Intent(MainActivity.this, Activity_Two.class);
                startActivity(intent_01);
            }
        });

        button_start_activity_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Activity_Three.class);
                startActivity(i);
            }
        });

        button_set_visibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_group_1.setVisibility(View.INVISIBLE);
                textview_main.setText("-= 這是主畫面，顯示狀態 : " + view_group_1.getVisibility());

//                Handler visibilityHandler = new Handler();
                Handler visibilityHandler;
                visibilityHandler = new Handler();

                visibilityHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view_group_1.setVisibility(View.VISIBLE);
                        textview_main.setText("-= 這是主畫面，顯示狀態 : " + view_group_1.getVisibility());
                    }
                }, 5000);

            }
        });


    }
}