package com.tondz.matchlove.Activity.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tondz.matchlove.Activity.Admin.Adapter.MusicListAdapter;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.Model.Music;
import com.tondz.matchlove.R;

public class ListMusicActivity extends AppCompatActivity {

    ListView lv_music;
    ImageView imgView;
    TextView tv_namelist;
    ImageView btn_play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_music);
        initView();
        Glide.with(ListMusicActivity.this).load(Common.album.getImage()).into(imgView);
        tv_namelist.setText(Common.album.getName());
        MusicListAdapter adapter = new MusicListAdapter(ListMusicActivity.this, Common.album.getMusicList());
        lv_music.setAdapter(adapter);
        lv_music.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PlayMusicActivity.index = i;
                startActivity(new Intent(ListMusicActivity.this,PlayMusicActivity.class));
            }
        });
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayMusicActivity.index= 0;
                startActivity(new Intent(ListMusicActivity.this,PlayMusicActivity.class));
            }
        });
    }
    private void initView(){
        lv_music = findViewById(R.id.lv_listmusic);
        imgView = findViewById(R.id.img_avatar);
        btn_play = findViewById(R.id.btn_playmusic);
        tv_namelist= findViewById(R.id.tv_namelist);
    }

}