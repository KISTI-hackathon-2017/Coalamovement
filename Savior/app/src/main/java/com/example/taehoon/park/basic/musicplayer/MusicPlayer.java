package com.example.taehoon.park.basic.musicplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.taehoon.park.R;

import java.io.IOException;

public class MusicPlayer extends AppCompatActivity {

    MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        music = MediaPlayer.create(this, R.raw.ms1);
        music.setLooping(true);
        music.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(music.isPlaying()){
            // 음악을 정지합니다
            music.stop();
            try {
                // 음악을 재생할경우를 대비해 준비합니다
                // prepare()은 예외가 2가지나 필요합니다
                music.prepare();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            music.seekTo(0);
        }
    }

}
