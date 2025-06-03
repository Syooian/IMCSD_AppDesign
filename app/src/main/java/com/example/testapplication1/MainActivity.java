package com.example.testapplication1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    CheckedTextView CheckedTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);//是否顯示上方的狀態列
        setContentView(R.layout.editor_layout);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        try {
            CheckedTextView2 = findViewById(R.id.Check2);
            CheckedTextView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckedTextView2.toggle();
                }
            });
        }
        catch (Exception e) {

        }
    }

    int ClickCount;

    public void BtnClick1(View V) {
        Log.v("TA1", "Button1 Click");

        TextView textView1 = findViewById(R.id.NumberText);
        textView1.setText(Integer.toString(++ClickCount));
    }

    //===========================================================editor_layout
    public void GoClick(View V) {
        EditText editText = GetEditText();

        var Value=editText.getText().toString()+"\n"+
                ((CheckedTextView)findViewById(R.id.Check1)).isChecked()+"\n"+
                ((CheckedTextView)findViewById(R.id.Check2)).isChecked()+"\n"+
                ((CheckBox)findViewById(R.id.Check3)).isChecked()+"\n"+
                ((CheckBox)findViewById(R.id.Check4)).isChecked();

        GetTextView().setText(Value);

        Log.v("TA1", "GoClick : " + Value);
    }

    public void Clean(View V) {
        GetEditText().setText("");
        GetTextView().setText("");
    }

    TextView GetTextView() {
        return findViewById(R.id.textView3);
    }

    EditText GetEditText() {
        return findViewById(R.id.editTextTextMultiLine);
    }
    //===========================================================editor_layout
}