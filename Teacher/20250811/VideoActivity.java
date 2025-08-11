package com.example.application2;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class VideoActivity extends AppCompatActivity {

    VideoView videoview_player;
    TextView textview_title;
    MediaController mcontroller;

    String uriResourceTitle = "android.resource://";
    String videoFileName = "point21";
    String strVideoResourceURI;
    int intVideoResourceId;
    int intPauseTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        videoview_player = findViewById(R.id.videoview_player);
        textview_title = findViewById(R.id.textview_title);

        mcontroller = new MediaController(VideoActivity.this);

//        intVideoResourceId = getResources().getIdentifier(videoFileName, "raw" ,getPackageName());
        intVideoResourceId = R.raw.point21;

        strVideoResourceURI = uriResourceTitle + getPackageName() + "/" + intVideoResourceId;

        videoview_player.setVideoURI(Uri.parse(strVideoResourceURI));
        videoview_player.setMediaController(mcontroller);
        videoview_player.start();

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (videoview_player != null) {
            if (videoview_player.isPlaying() == true) {
                intPauseTime = videoview_player.getCurrentPosition();
                videoview_player.pause();
                textview_title.setText("影片撥放器 : 播放暫停於[" + String.valueOf(intPauseTime/1000) + "." + String.valueOf(intPauseTime%1000) + "]秒");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (videoview_player != null) {
            if(intPauseTime != 0) {
                videoview_player.seekTo(intPauseTime);
            }
            videoview_player.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (videoview_player != null){
            if (videoview_player.isPlaying() );
            videoview_player.stopPlayback();
        }

        videoview_player = null;
        mcontroller = null;
    }
}