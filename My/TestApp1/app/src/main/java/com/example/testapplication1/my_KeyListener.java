package com.example.testapplication1;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class my_KeyListener extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.my_key_listener);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView Text = findViewById(R.id.KL_TextView);

        Text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                //按下Enter
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            Text.setText("按下了Enter鍵");
                            break;
                        case KeyEvent.KEYCODE_NUMPAD_0:
                        case KeyEvent.KEYCODE_NUMPAD_1:
                        case KeyEvent.KEYCODE_NUMPAD_2:
                        case KeyEvent.KEYCODE_NUMPAD_3:
                        case KeyEvent.KEYCODE_NUMPAD_4:
                        case KeyEvent.KEYCODE_NUMPAD_5:
                        case KeyEvent.KEYCODE_NUMPAD_6:
                        case KeyEvent.KEYCODE_NUMPAD_7:
                        case KeyEvent.KEYCODE_NUMPAD_8:
                        case KeyEvent.KEYCODE_NUMPAD_9:
                            Text.setText("按下了數字鍵: " + (keyCode - 144));
                            break;
                    }

                    return true; //返回true表示事件已被處理
                }

                return false;
            }
        });
    }
}