package com.example.application2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MusicActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    MediaPlayer playerMP3;
    Button button_start, button_pause, button_stop;
    TextView textview_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_music);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        playerMP3 = MediaPlayer.create(MusicActivity.this, R.raw.piano01);

        button_start = findViewById(R.id.button_start);
        button_pause = findViewById(R.id.button_pause);
        button_stop = findViewById(R.id.button_stop);
        textview_status = findViewById(R.id.textview_status);

        playerMP3.setOnCompletionListener(MusicActivity.this);

        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerMP3 != null) {
                    if (playerMP3.isPlaying() == false) {
                        playerMP3.start();
                        textview_status.setText("播放狀態 : 音樂播放中...");
                    }
                }
            }
        });

        button_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerMP3 != null) {
                    if (playerMP3.isPlaying() == true) {
                        playerMP3.pause();
                        textview_status.setText("播放狀態 : 音樂暫停播放...");
                    }
                }
            }
        });

        button_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerMP3 != null) {
                    playerMP3.stop();
                    textview_status.setText("播放狀態 : 音樂停止播放 !!!");

                    try {
                        playerMP3.prepareAsync();
                    } catch (IllegalStateException e) {
                        textview_status.setText("發生錯誤 : " + e.getMessage());
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (playerMP3 != null) {
            if (playerMP3.isPlaying()) {
                playerMP3.stop();
            }
            playerMP3.release();
        }
        playerMP3 = null;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        textview_status.setText("播放狀態 : 整首音樂撥放完畢 。");
        playerMP3.seekTo(0);

        try {
            playerMP3.prepareAsync();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
}