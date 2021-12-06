package com.tondz.matchlove.Activity.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.FirebaseContext.MusicDBContext;
import com.tondz.matchlove.Model.Music;
import com.tondz.matchlove.R;

import java.util.Random;

public class InputMusicActivity extends AppCompatActivity {

    EditText edt_link, edt_name;
    Button btn_update, btn_delete;
    MusicDBContext dbContext;
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_music);
        initView();

        if (Common.music != null) {
            btn_delete.setVisibility(View.VISIBLE);
            btn_update.setText("Cập nhật");
            edt_link.setText(Common.music.getLink());
            edt_name.setText(Common.music.getName());
            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Common.music.setLink(edt_link.getText().toString());
                    Common.music.setName(edt_name.getText().toString());
                    dbContext.update(Common.music, InputMusicActivity.this);
                    Common.music = null;
                    btn_update.setText("Thêm");
                    btn_delete.setVisibility(View.GONE);
                    finish();
                }
            });
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btn_update.setText("Thêm");
                    dbContext.delete(Common.music.getId(), InputMusicActivity.this);
                    Common.music = null;
                    finish();
                }
            });
        } else {
            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Music music = new Music(random.nextInt() + "", edt_link.getText().toString(), edt_name.getText().toString(), 0);
                    dbContext.update(music, InputMusicActivity.this);
                    finish();
                }
            });
        }
    }

    private void initView() {
        edt_link = findViewById(R.id.edt_link);
        edt_name = findViewById(R.id.edt_namemusic);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);
        dbContext = new MusicDBContext();
        random = new Random();
    }
}