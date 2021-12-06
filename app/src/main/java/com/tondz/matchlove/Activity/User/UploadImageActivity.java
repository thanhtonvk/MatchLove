package com.tondz.matchlove.Activity.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.tondz.matchlove.Activity.User.Adapter.ListImageAdapter;
import com.tondz.matchlove.FirebaseContext.AccountDBContext;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.R;

import java.util.ArrayList;
import java.util.List;

public class UploadImageActivity extends AppCompatActivity {

    Button btn_add,btn_back;
    GridView gridView;
    AccountDBContext dbContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        initView();
        loadListImage();
        onClick();

    }

    private void onClick(){
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView img = view.findViewById(R.id.img_slider);
                Common.showDialogImage(img,UploadImageActivity.this);
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(UploadImageActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_GALERY);
            }
        });
    }
    int REQUEST_GALERY = 123;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_GALERY && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_GALERY);
        } else {
            Toast.makeText(getApplicationContext(), "Bạn chưa cấp quyền camera", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            Common.account.getImages().add("");
            dbContext.uploadImage(uri, Common.account, Common.account.getImages().size()-1, UploadImageActivity.this);
        }
    }

    private void initView(){
        btn_add = findViewById(R.id.btn_add);
        btn_back = findViewById(R.id.btn_back);
        gridView= findViewById(R.id.grid_imamge);
        dbContext = new AccountDBContext();
    }
    private void loadListImage(){
        List<String>images = new ArrayList<>();
        for (String img:Common.account.getImages()
             ) {
            if(!img.equals("")) images.add(img);
        }
        ListImageAdapter imageAdapter =new ListImageAdapter(UploadImageActivity.this, images);
        gridView.setAdapter(imageAdapter);
    }
}