package com.example.intent06;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activity_Two extends AppCompatActivity {

    Button button_close_activity_two, button_start_activity_three;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_two);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        button_close_activity_two = findViewById(R.id.button_close_activity_two);
        button_start_activity_three = findViewById(R.id.button_start_activity_three);

        button_close_activity_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button_start_activity_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Two.this, Activity_Three.class);
                startActivity(i);
            }
        });

        Log.d("Intent06 : " , "Activity_Two - onCreate() 被執行!!");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("Intent06 : " , "Activity_Two - onStart() 被執行!!");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("Intent06 : " , "Activity_Two - onResume() 被執行!!");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("Intent06 : " , "Activity_Two - onPause() 被執行!!");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("Intent06 : " , "Activity_Two - onStop() 被執行!!");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d("Intent06 : " , "Activity_Two - onRestart() 被執行!!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("Intent06 : " , "Activity_Two - onDestroy() 被執行!!");
    }
}