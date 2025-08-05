package com.example.intent03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityOperate extends AppCompatActivity {

    RadioButton radiobutton_add, radiobutton_subtract, radiobutton_multiply, radiobutton_divide;
    CheckBox checkbox_divide;
    Button button_calculate;
    Double op_01, op_02, result;

    TextView textview_message;

    boolean operate_status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_operate);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        radiobutton_add = findViewById(R.id.radiobutton_add);
        radiobutton_subtract = findViewById(R.id.radiobutton_subtract);
        radiobutton_multiply = findViewById(R.id.radiobutton_multiply);
        radiobutton_divide = findViewById(R.id.radiobutton_divide);
        checkbox_divide = findViewById(R.id.checkbox_divide);
        button_calculate = findViewById(R.id.button_calculate);
        textview_message = findViewById(R.id.textview_message);

        button_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_getOperate = ActivityOperate.this.getIntent();
                Bundle bundle_getOperate = intent_getOperate.getExtras();

                if (bundle_getOperate != null) {
                    String temp = bundle_getOperate.getString("OPERAND_01");
                    op_01 = Double.parseDouble(temp);
                    op_02 = Double.parseDouble(bundle_getOperate.getString("OPERAND_02"));

                    if(radiobutton_add.isChecked()) {
                        result = op_01 + op_02;
                        operate_status = true;
                        textview_message.setText("");
                    }else if(radiobutton_subtract.isChecked()) {
                        result = op_01 - op_02;
                        operate_status = true;
                        textview_message.setText("");
                    }else if(radiobutton_multiply.isChecked()) {
                        result = op_01 * op_02;
                        operate_status = true;
                        textview_message.setText("");
                    }else if(radiobutton_divide.isChecked()) {
                        if(checkbox_divide.isChecked()) {
                            result = Math.floor(op_01 / op_02);
                        } else {
                            result = op_01 / op_02;
                        }
                        operate_status = true;
                        textview_message.setText("");
                    } else {
                        textview_message.setText("未選取任何運算子符號，請重新選擇!! ");
                        operate_status = false;
                    }

                    if (operate_status == true) {
                        Intent return_intent = new Intent();
                        Bundle return_bundle = new Bundle();
                        return_bundle.putDouble("CALCULATE_RESULT", result);
                        return_intent.putExtras(return_bundle);
                        setResult(RESULT_OK, return_intent);
                        finish();
                    }

                } else {
                    textview_message.setText("發生錯誤 : bundle_getOperate 為 Null");
                }
            }
        });
    }
}