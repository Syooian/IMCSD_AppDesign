package com.example.testapplication1;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class my_Activity_Homework_ConstraintLayout extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);//是否顯示上方的狀態列

        setContentView(R.layout.my_homework_constraintlayout);

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        //取得Intent傳來的值
        int Number = getIntent().getIntExtra("Number", 0);

        //修改文字
        TextView TitleTextView = findViewById(R.id.TitleTextView);
        TitleTextView.setText(TitleTextView.getText() + "\n" + Number);
    }
}