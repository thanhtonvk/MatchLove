package com.tondz.matchlove.Activity.User;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.Model.Music;
import com.tondz.matchlove.R;

import java.io.IOException;

public class PlayMusicActivity extends AppCompatActivity {

    ImageView img_avatar;
    TextView tv_name, tv_current, tv_duraton;
    SeekBar seekBar;
    ImageView btn_play, btn_prev, btn_next;
    MediaPlayer mediaPlayer = new MediaPlayer();
    Handler handler = new Handler();
    public static int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        initView();
        Glide.with(PlayMusicActivity.this).load(Common.album.getImage()).into(img_avatar);
        Animation animation = AnimationUtils.loadAnimation(PlayMusicActivity.this, R.anim.rotation_animation);
        img_avatar.setAnimation(animation);
        loadMusic();
        setSeekBarTouch();
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    btn_play.setImageResource(R.drawable.ic_play);
                } else {
                    mediaPlayer.start();
                    btn_play.setImageResource(R.drawable.ic_pause);
                    updateSeekBar();
                }
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index++;
                if(index==Common.album.getMusicList().size()){
                    index = 0;
                }
                loadMusic();
            }
        });
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index--;
                if(index==-1){
                    index = Common.album.getMusicList().size()-1;
                }
                loadMusic();
            }
        });
    }

    private void loadMusic() {
        Music music = Common.album.getMusicList().get(index);
        tv_name.setText(music.getName());
        prepareMediaPlay(music.getLink());
    }

    private void initView() {
        img_avatar = findViewById(R.id.img_avatar);
        tv_name = findViewById(R.id.tv_name);
        tv_current = findViewById(R.id.tv_current_music);
        tv_duraton = findViewById(R.id.tv_duration_music);
        seekBar = findViewById(R.id.seekbar_music);
        btn_play = findViewById(R.id.btn_playmusic);
        btn_prev = findViewById(R.id.btn_previous);
        btn_next = findViewById(R.id.btn_next);
    }

    //set play music
    public String miliSecondsToTime(long miliSeconds) {
        String timerString = "";
        String secondStirng;
        int hour = (int) (miliSeconds / (1000 * 60 * 60));
        int minutes = (int) (miliSeconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((miliSeconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        if (hour > 0) {
            timerString = hour + ":";
        }
        if (seconds < 10) {
            secondStirng = "0" + seconds;
        } else {
            secondStirng = "" + seconds;
        }
        timerString = timerString + minutes + ":" + secondStirng;
        return timerString;

    }

    private void updateSeekBar() {
        if (mediaPlayer.isPlaying()) {
            seekBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100));
            handler.postDelayed(updater, 1000);
        }
    }

    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            tv_current.setText(miliSecondsToTime(currentDuration));
        }
    };

    public void prepareMediaPlay(String source) {
        try {
            mediaPlayer.stop();
            mediaPlayer = new MediaPlayer();
            //load nhạc từ link
            mediaPlayer.setDataSource(source);
            mediaPlayer.prepare();
            tv_duraton.setText(miliSecondsToTime(mediaPlayer.getDuration()));
            mediaPlayer.start();
            updateSeekBar();
            btn_play.setImageResource(R.drawable.ic_pause);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSeekBarTouch() {
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SeekBar seekBar = (SeekBar) view;
                int playPosition = (mediaPlayer.getDuration() / 100) * seekBar.getProgress();
                mediaPlayer.seekTo(playPosition);
                tv_current.setText(miliSecondsToTime(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });
    }
}