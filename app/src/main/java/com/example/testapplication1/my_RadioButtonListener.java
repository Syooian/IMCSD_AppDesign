package com.example.testapplication1;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class my_RadioButtonListener extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.my_radio_button_listener);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //具名委派
        RadioButton.OnCheckedChangeListener CheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ShowToast(buttonView.getText().toString());
            }
        };

        //匿名委派
        RadioButton RB1 = findViewById(R.id.RB1);
        RB1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ShowToast(buttonView.getText().toString());
            }
        });

        for(int i = 1; i < 5; i++) {
            //具名委派
            RadioButton RB = findViewById(R.id.RB1 + i);
            RB.setOnCheckedChangeListener(CheckedChangeListener);
        }
    }


    public void ShowToast(String Name) {
        //顯示彈出訊息
        Toast T = Toast.makeText(this, Name + " 被選中", Toast.LENGTH_SHORT);
        T.show();
    }
}