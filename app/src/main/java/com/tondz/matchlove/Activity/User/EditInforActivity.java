package com.tondz.matchlove.Activity.User;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.tondz.matchlove.FirebaseContext.AccountDBContext;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.R;

import java.util.ArrayList;
import java.util.List;

public class EditInforActivity extends AppCompatActivity {

    Button btn_back, btn_update;
    ImageView img0, img1, img2, img3, img4, img5, img6, img7, img8;
    EditText edt_status, edt_name, edt_address, edt_job, edt_school, edt_company, edt_hobies;
    TextView tv_date;
    RadioButton rd_male, rd_female;
    List<String> images;
    AccountDBContext dbContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_infor);
        initView();
        loadInfo();
        onClick();


    }


    //set uploadImage
    final int REQUEST_GALERY = 123;

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

    int indexImage;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            dbContext.uploadImage(uri, Common.account, indexImage, EditInforActivity.this);
        }
    }

    private void onClick() {
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDatePicker();
            }
        });
        img0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(EditInforActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_GALERY);
                indexImage = 0;
            }
        });
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(EditInforActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_GALERY);
                indexImage = 1;
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(EditInforActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_GALERY);
                indexImage = 2;
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(EditInforActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_GALERY);
                indexImage = 3;
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(EditInforActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_GALERY);
                indexImage = 4;
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(EditInforActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_GALERY);
                indexImage = 5;
            }
        });
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(EditInforActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_GALERY);
                indexImage = 6;
            }
        });
        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(EditInforActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_GALERY);
                indexImage = 7;
            }
        });
        img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(EditInforActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_GALERY);
                indexImage = 8;
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInfor();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        btn_back = findViewById(R.id.btn_back);
        btn_update = findViewById(R.id.btn_update);
        img0 = findViewById(R.id.img_avt0);
        img1 = findViewById(R.id.img_avt1);
        img2 = findViewById(R.id.img_avt2);
        img3 = findViewById(R.id.img_avt3);
        img4 = findViewById(R.id.img_avt4);
        img5 = findViewById(R.id.img_avt5);
        img6 = findViewById(R.id.img_avt6);
        img7 = findViewById(R.id.img_avt7);
        img8 = findViewById(R.id.img_avt8);
        edt_status = findViewById(R.id.edt_status);
        edt_name = findViewById(R.id.edt_name);
        edt_address = findViewById(R.id.edt_address);
        edt_job = findViewById(R.id.edt_job);
        edt_school = findViewById(R.id.edt_school);
        edt_company = findViewById(R.id.edt_company);
        tv_date = findViewById(R.id.edt_date);
        rd_male = findViewById(R.id.rd_nam);
        rd_female = findViewById(R.id.rd_nu);
        images = new ArrayList<>();
        dbContext = new AccountDBContext();
        edt_hobies = findViewById(R.id.edt_hobbies);
    }

    //Set Datepicker
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDatePicker() {
        String[] arr = Common.account.getDateOfBirth().split("-");

        int selectedYear = Integer.parseInt(arr[2].trim());
        int selectedMonth = Integer.parseInt(arr[1].trim());
        int selectedDay = Integer.parseInt(arr[0].trim());
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                tv_date.setText(String.format("%s - %s - %s", i2, i1 + 1, i));
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(EditInforActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, dateSetListener, selectedYear, selectedMonth, selectedDay);
        datePickerDialog.show();
    }

    private void updateInfor() {
        Common.account.setStatus(edt_status.getText().toString().trim());
        Common.account.setFullName(edt_name.getText().toString().trim());
        Common.account.setAddress(edt_address.getText().toString().trim());
        Common.account.setDateOfBirth(tv_date.getText().toString().trim());
        if (rd_male.isChecked()) {
            Common.account.setSex("nam");
        } else {
            Common.account.setSex("nữ");
        }
        Common.account.setJob(edt_job.getText().toString().trim());
        Common.account.setSchool(edt_school.getText().toString().trim());
        Common.account.setCompany(edt_company.getText().toString().trim());
        Common.account.setAvatar(images.get(0));
        List<String> listHobbies = new ArrayList<>();
        for (String str : edt_hobies.getText().toString().split(",")
        ) {
            listHobbies.add(str.trim());
        }
        Common.account.setHobbies(listHobbies);
        Common.account.setFirstSetup(false);
        dbContext.update(Common.account, EditInforActivity.this);
    }


    private void loadInfo() {
        if (Common.account != null) {
            edt_status.setText(Common.account.getStatus());
            edt_name.setText(Common.account.getFullName());
            edt_address.setText(Common.account.getAddress());
            edt_job.setText(Common.account.getJob());
            edt_school.setText(Common.account.getSchool());
            edt_company.setText(Common.account.getCompany());
            tv_date.setText(Common.account.getDateOfBirth());
            if (Common.account.getSex() != null) {
                if (Common.account.getSex().equalsIgnoreCase("nam")) {
                    rd_male.setChecked(true);
                } else {
                    rd_female.setChecked(true);
                }
            }
            if (Common.account.getHobbies() != null) {
                String hobbies = "";
                for (String str : Common.account.getHobbies()
                ) {
                    if (!str.equals("")) {
                        hobbies += str + ",";
                    }
                }
                edt_hobies.setText(hobbies);
            }
            if (Common.account.getImages() != null) {
                images = Common.account.getImages();
                if (!images.get(0).equals("")) {
                    dbContext.getStorageReference().child(Common.account.getId()).child(images.get(0)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(EditInforActivity.this).load(uri).into(img0);
                        }
                    });
                }

                if (!images.get(1).equals("")) {
                    dbContext.getStorageReference().child(Common.account.getId()).child(images.get(1)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(EditInforActivity.this).load(uri).into(img1);
                        }
                    });
                }
                if (!images.get(2).equals("")) {
                    dbContext.getStorageReference().child(Common.account.getId()).child(images.get(2)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(EditInforActivity.this).load(uri).into(img2);
                        }
                    });
                }
                if (!images.get(3).equals("")) {
                    dbContext.getStorageReference().child(Common.account.getId()).child(images.get(3)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(EditInforActivity.this).load(uri).into(img3);
                        }
                    });
                }
                if (!images.get(4).equals("")) {
                    dbContext.getStorageReference().child(Common.account.getId()).child(images.get(4)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(EditInforActivity.this).load(uri).into(img4);
                        }
                    });
                }
                if (!images.get(5).equals("")) {
                    dbContext.getStorageReference().child(Common.account.getId()).child(images.get(5)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(EditInforActivity.this).load(uri).into(img5);
                        }
                    });
                }
                if (!images.get(6).equals("")) {
                    dbContext.getStorageReference().child(Common.account.getId()).child(images.get(6)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(EditInforActivity.this).load(uri).into(img6);
                        }
                    });
                }
                if (!images.get(7).equals("")) {
                    dbContext.getStorageReference().child(Common.account.getId()).child(images.get(7)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(EditInforActivity.this).load(uri).into(img7);
                        }
                    });
                }
                if (!images.get(8).equals("")) {
                    dbContext.getStorageReference().child(Common.account.getId()).child(images.get(8)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(EditInforActivity.this).load(uri).into(img8);
                        }
                    });
                }
            }
        }
    }
}