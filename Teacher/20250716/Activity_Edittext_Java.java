package com.example.application1;

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

public class Activity_Edittext_Java extends AppCompatActivity {

    Button btn_add;
    TextView textview_answer;
    EditText edittext_input01,edittext_input02;
    Double double_01, double_02, double_answer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edittext_java);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        btn_add = findViewById(R.id.btn_add);
        textview_answer = findViewById(R.id.textview_answer);
        edittext_input01 = findViewById(R.id.edittext_input01);
        edittext_input02 = findViewById(R.id.edittext_input02);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTemp;
                strTemp = edittext_input01.getText().toString();
                double_01 = Double.parseDouble(strTemp);
                strTemp = edittext_input02.getText().toString();
                double_02 = Double.parseDouble(strTemp);
                double_answer = double_01 + double_02;

                textview_answer.setText(" = " + double_answer.toString());
            }
        });

    }
}