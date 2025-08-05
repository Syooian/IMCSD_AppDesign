package com.example.intent03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText edittext_input_opd1, edittext_input_opd2;
    TextView textview_output;

    Button button_choice_op;

    private static final int REQUEST_CODE_OP = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 //       EdgeToEdge.enable(this);
        setContentView(R.layout.activity_operate);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        edittext_input_opd1 = findViewById(R.id.edittext_input_opd1);
        edittext_input_opd2 = findViewById(R.id.edittext_input_opd2);
        textview_output = findViewById(R.id.textview_output);
        button_choice_op = findViewById(R.id.button_choice_op);

        button_choice_op.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_callActivityOperate = new Intent(MainActivity.this, ActivityOperate.class);
                Bundle bundle_operand = new Bundle();

                bundle_operand.putString("OPERAND_01", edittext_input_opd1.getText().toString());
                bundle_operand.putString("OPERAND_02", edittext_input_opd2.getText().toString());

                intent_callActivityOperate.putExtras(bundle_operand);
                startActivityForResult(intent_callActivityOperate, REQUEST_CODE_OP);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == REQUEST_CODE_OP) {
        if (resultCode == RESULT_OK) {
            Bundle bundle_resultReturn = data.getExtras();

            if (bundle_resultReturn != null) {
                Double answer = bundle_resultReturn.getDouble("CALCULATE_RESULT");
                textview_output.setText("運算結果 = " + answer.toString());
            }
        }
      }
    }
}