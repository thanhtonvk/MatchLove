package com.tondz.matchlove.Activity.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.FirebaseContext.VideoDBContext;
import com.tondz.matchlove.Model.Video;
import com.tondz.matchlove.R;

public class InputVideoActivity extends AppCompatActivity {

    EditText edt_id, edt_name;
    Button btn_update, btn_delete;
    VideoDBContext dbContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_video);
        initView();
        if (Common.video != null) {
            btn_delete.setVisibility(View.VISIBLE);
            btn_update.setText("Cập nhật");
            edt_id.setText(Common.video.getId());
            edt_name.setText(Common.video.getName());
            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Common.video.setName(edt_name.getText().toString());
                    dbContext.update(Common.video, InputVideoActivity.this);
                    Common.video = null;
                    btn_update.setText("Thêm");
                    btn_delete.setVisibility(View.GONE);
                    finish();
                }
            });
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btn_update.setText("Thêm");
                    dbContext.delete(Common.video.getId(), InputVideoActivity.this);
                    Common.video = null;
                    finish();
                }
            });
        } else {
            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Video video = new Video(edt_id.getText().toString(),edt_name.getText().toString(),0);
                    dbContext.update(video, InputVideoActivity.this);
                    finish();
                }
            });
        }


    }

    private void initView() {
        edt_id = findViewById(R.id.edt_idvideo);
        edt_name = findViewById(R.id.edt_namevideo);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);
        dbContext = new VideoDBContext();
    }
}