package com.tondz.matchlove.Activity.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.tondz.matchlove.FirebaseContext.AccountDBContext;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.R;

import java.util.ArrayList;
import java.util.List;

public class ViewProfileActivity extends AppCompatActivity {

    ImageView img_avatar;
    TextView tv_name, tv_sex, tv_address, tv_dateofbirth, tv_school, tv_job, tv_company, tv_status;
    AccountDBContext dbContext;
    ImageSlider imageSlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        initView();
        if (Common.AccountChat != null) {
            tv_name.setText(Common.AccountChat.getFullName());
            tv_sex.setText("Giới tính: " + Common.AccountChat.getSex());
            tv_address.setText("Sống tại: " + Common.AccountChat.getAddress());
            tv_dateofbirth.setText("Sinh ngày: " + Common.AccountChat.getDateOfBirth());
            tv_school.setText("Trường học: " + Common.AccountChat.getSchool());
            tv_job.setText("Công việc: " + Common.AccountChat.getJob());
            tv_company.setText("Công ty: " + Common.AccountChat.getCompany());
            tv_status.setText(Common.AccountChat.getStatus());
            loadImage();
        }
    }

    private void loadImage(){
        List<SlideModel>listImage = new ArrayList<>();
        for (String img:Common.AccountChat.getImages()
             ) {
            if(!img.equals("")){
                dbContext.getStorageReference().child(Common.AccountChat.getId()).child(img).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        SlideModel model = new SlideModel(uri.toString(), ScaleTypes.CENTER_CROP);
                        listImage.add(model);
                        imageSlider.setImageList(listImage);
                    }
                });
            }

        }
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
        dbContext = new AccountDBContext();
        imageSlider = findViewById(R.id.imageSlider);
        imageSlider.startSliding(3000);

    }
}