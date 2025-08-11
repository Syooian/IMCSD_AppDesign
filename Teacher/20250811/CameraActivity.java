package com.example.application2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CameraActivity extends AppCompatActivity {

    ImageView imageview_photo;
    Button button_start_camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_camera);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        imageview_photo = findViewById(R.id.imageview_photo);
        button_start_camera = findViewById(R.id.button_start_camera);

        button_start_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                launcher_captureImage.launch(i);
            }
        });
    }


    ActivityResultLauncher launcher_captureImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                                                                             new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode()  == RESULT_OK) {
                        Intent intent_returned = o.getData();
                        Bundle bundle_image = intent_returned.getExtras();
                        if (bundle_image != null) {
                            Bitmap bitmap_returned_photo = (Bitmap) bundle_image.get("data");
                            imageview_photo.setImageBitmap(bitmap_returned_photo);
                        }
                    }
                }
            });

}