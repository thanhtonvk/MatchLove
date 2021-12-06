package com.tondz.matchlove.Activity.User;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.tondz.matchlove.FirebaseContext.AccountDBContext;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.R;

public class InfomationActivity extends AppCompatActivity {

    ImageView img_avatar;
    TextView tv_name, tv_sex, tv_address, tv_dateofbirth, tv_school, tv_job, tv_company, tv_status;
    ImageView btn_edit;
    AccountDBContext dbContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);
        initView();
        if (Common.account != null) {
            Glide.with(InfomationActivity.this).load(Common.account.getAvatar()).into(img_avatar);
            tv_name.setText(Common.account.getFullName());
            tv_sex.setText("Giới tính: " + Common.account.getSex());
            tv_address.setText("Sống tại: " + Common.account.getAddress());
            tv_dateofbirth.setText("Sinh ngày: " + Common.account.getDateOfBirth());
            tv_school.setText("Trường học: " + Common.account.getSchool());
            tv_job.setText("Công việc: " + Common.account.getJob());
            tv_company.setText("Công ty: " + Common.account.getCompany());
            tv_status.setText(Common.account.getStatus());
            if (!Common.account.getAvatar().equals("")) {
                dbContext.getStorageReference().child(Common.account.getId()).child(Common.account.getAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(InfomationActivity.this).load(uri).into(img_avatar);
                    }
                });
            }
        }
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditInforActivity.class));
            }
        });
        img_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.showDialogImage(img_avatar,InfomationActivity.this);
            }
        });
    }

    private void initView() {
        img_avatar = findViewById(R.id.img_avatar);
        tv_name = findViewById(R.id.tv_name);
        tv_sex = findViewById(R.id.tv_sex);
        tv_address = findViewById(R.id.tv_address);
        tv_dateofbirth = findViewById(R.id.tv_dateofbirth);
        tv_school = findViewById(R.id.tv_school);
        tv_job = findViewById(R.id.tv_job);
        tv_company = findViewById(R.id.tv_company);
        tv_status = findViewById(R.id.tv_status);
        btn_edit = findViewById(R.id.btn_edit);
        dbContext = new AccountDBContext();
    }
}