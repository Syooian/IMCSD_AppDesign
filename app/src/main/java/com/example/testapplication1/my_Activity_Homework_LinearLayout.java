package com.example.testapplication1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class my_Activity_Homework_LinearLayout extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);//是否顯示上方的狀態列

        setContentView(R.layout.my_homework_linearlayout);

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }
}