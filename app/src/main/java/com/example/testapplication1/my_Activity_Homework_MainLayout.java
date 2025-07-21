package com.example.testapplication1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class my_Activity_Homework_MainLayout extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);//是否顯示上方的狀態列

        setContentView(R.layout.my_homework_main);

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        Button Btn_Constraint = findViewById(R.id.Btn_Constraint);
        Btn_Constraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoTo("my_Activity_Homework_ConstraintLayout", 999);
            }
        });

        Button Btn_Linear = findViewById(R.id.Btn_Linear);
        Btn_Linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoTo("my_Activity_Homework_LinearLayout", null);
            }
        });

        Button Btn_Relative = findViewById(R.id.Btn_Relative);
        Btn_Relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoTo("my_Activity_Homework_RelativeLayout", null);
            }
        });
    }


    public void GoTo(String ActivityName, Integer Number) {
        try {
            Class<?> cls = Class.forName("com.example.testapplication1." + ActivityName);

            Intent Intent = new Intent(this, cls);

            if (Number != null)
                Intent.putExtra("Number", Number);

            startActivity(Intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}