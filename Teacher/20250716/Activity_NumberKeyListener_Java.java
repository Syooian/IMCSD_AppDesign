package com.example.application1;

import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Activity_NumberKeyListener_Java extends AppCompatActivity {

    Button btn_add;
    TextView textview_add, textview_answer;
    EditText edittext_input01,edittext_input02;

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
        textview_add = findViewById(R.id.textview_add);
        textview_answer = findViewById(R.id.textview_answer);
        edittext_input01 = findViewById(R.id.edittext_input01);
        edittext_input02 = findViewById(R.id.edittext_input02);

        edittext_input01.setKeyListener(new NumberKeyListener() {
            @NonNull
            @Override
            protected char[] getAcceptedChars() {

                char acceptChars[] = {'0', '1', '2', '3', '4', '5'};

                return acceptChars;
            }

            @Override
            public int getInputType() {
                return 0;
            }
        });

        edittext_input02.setKeyListener(new NumberKeyListener() {
            @NonNull
            @Override
            protected char[] getAcceptedChars() {

                char acceptChars[] =  {'6', '7', '8', '9', 'A', 'B'};

                return acceptChars;
            }

            @Override
            public int getInputType() {
                return 0;
            }
        });


    }
}