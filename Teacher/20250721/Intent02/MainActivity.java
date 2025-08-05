package com.example.intent02;

import android.content.Intent;
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

    Button button_ctoF;
    EditText edittext_input_c;

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

        button_ctoF = findViewById(R.id.button_ctoF);
        edittext_input_c = findViewById(R.id.edittext_input_c);

        button_ctoF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_Call_TemperatureF = new Intent(MainActivity.this, ActivityTemperatureF.class);

                Bundle bundle_TempC = new Bundle();
                bundle_TempC.putString("TEMP_C_01", edittext_input_c.getText().toString());

                intent_Call_TemperatureF.putExtras(bundle_TempC);

                startActivity(intent_Call_TemperatureF);
            }
        });

    }
}