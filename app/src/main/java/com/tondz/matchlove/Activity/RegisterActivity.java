package com.tondz.matchlove.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.tondz.matchlove.FirebaseContext.AccountDBContext;
import com.tondz.matchlove.Model.Account;
import com.tondz.matchlove.R;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    EditText edt_email, edt_password, edt_name;
    Button btn_register;
    Animation animation;
    AccountDBContext accountDBContext;
    TextView edt_date;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        accountDBContext = new AccountDBContext();
        try {
            setAnimation();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onClick();


    }

    private void setAnimation() throws InterruptedException {
        edt_email.setAnimation(animation);

        edt_password.setAnimation(animation);

        edt_name.setAnimation(animation);

        edt_date.setAnimation(animation);

        btn_register.setAnimation(animation);

        linearLayout.setAnimation(animation);
    }

    private void initView() {
        edt_email = findViewById(R.id.edt_email_register);
        edt_password = findViewById(R.id.edt_password_register);
        edt_name = findViewById(R.id.edt_name_register);
        edt_date = findViewById(R.id.edt_date_register);
        btn_register = findViewById(R.id.btn_register_register);
        linearLayout = findViewById(R.id.layout_logo);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein_animation_register);
    }

    //Onclick event

    private void onClick() {
        edt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDatePicker();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_email.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Kh??ng ???????c ????? tr???ng email", Toast.LENGTH_SHORT).show();
                }
                if (edt_password.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Kh??ng ???????c ????? tr???ng m???t kh???u", Toast.LENGTH_SHORT).show();
                }
                if (edt_name.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Kh??ng ???????c ????? tr???ng t??n", Toast.LENGTH_SHORT).show();
                }
                if (edt_date.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Kh??ng ???????c ????? tr???ng ng??y sinh", Toast.LENGTH_SHORT).show();
                }
                if(!checkLimitAge()){
                    Toast.makeText(RegisterActivity.this, "B???n ph???i l???n h??n 12 tu???i", Toast.LENGTH_SHORT).show();
                }
                else {
                    Account account = new Account(edt_email.getText().toString(), edt_password.getText().toString(), edt_name.getText().toString(), edt_date.getText().toString());
                    List<String> images = new ArrayList<>();
                    images.add("");
                    images.add("");
                    images.add("");
                    images.add("");
                    images.add("");
                    images.add("");
                    images.add("");
                    images.add("");
                    images.add("");
                    String avatar = images.get(0);
                    account.setImages(images);
                    account.setAvatar(avatar);
                    register(account);
                }
            }
        });
    }

    //Set Datepicker

    private void setDatePicker() {
        int selectedYear = 0;
        int selectedMonth = 1;
        int selectedDay = 1;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            selectedYear = java.time.LocalDate.now().getYear();
            selectedMonth = java.time.LocalDate.now().getMonthValue();
            selectedDay = java.time.LocalDate.now().getDayOfMonth();
        }

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                edt_date.setText(String.format("%s - %s - %s", i2, i1, i));
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, dateSetListener, selectedYear, selectedMonth, selectedDay);
        datePickerDialog.show();
    }


    public boolean checkLimitAge(){
        int yearNow = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            yearNow = java.time.LocalDate.now().getYear();
        }
        int pickYear = Integer.parseInt(edt_date.getText().toString().split("-")[2].trim());
        if(yearNow-pickYear>12) return true;
        else return false;
    }
    //Register account
    private void register(Account account) {
        AlertDialog progress = new SpotsDialog(RegisterActivity.this, R.style.custom_register);
        progress.show();
        accountDBContext.getAuth().createUserWithEmailAndPassword(account.getEmail(), account.getPassWord()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = accountDBContext.getAuth().getCurrentUser();
                    account.setId(firebaseUser.getUid());
                    account.setSpace(30);
                    account.setAgefrom(18);
                    account.setAgeto(30);
                    account.setFemale(true);
                    account.setMale(false);
                    accountDBContext.getReference().child(account.getId()).setValue(account).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progress.dismiss();
                                Toast.makeText(RegisterActivity.this, "????ng k?? t??i kho???n th??nh c??ng", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "????ng k?? t??i kho???n kh??ng th??nh c??ng, ki???m tra l???i Email nh??", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            }
        });
    }
}