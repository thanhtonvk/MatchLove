package com.tondz.matchlove.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tondz.matchlove.Activity.Admin.AdminActivity;
import com.tondz.matchlove.Activity.User.UserActivity;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.FirebaseContext.AccountDBContext;
import com.tondz.matchlove.MainActivity;
import com.tondz.matchlove.Model.Account;
import com.tondz.matchlove.R;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {
    ImageView img_logo;
    TextView tv_logo;
    EditText edt_email, edt_password;
    Button btn_login_choose, btn_register_choose;
    Button btn_login, btn_register;
    AccountDBContext accountDBContext;
    int REQUEST_LOCATION = 111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        accountDBContext = new AccountDBContext();
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_animation);
        img_logo.setAnimation(animation);
        tv_logo.setAnimation(animation);
        onClick();

    }

    private void initView() {
        img_logo = findViewById(R.id.img_logo);
        tv_logo = findViewById(R.id.tv_logo);
        edt_email = findViewById(R.id.edt_email_login);
        edt_password = findViewById(R.id.edt_password_login);
        btn_login = findViewById(R.id.btn_login_login);
        btn_register = findViewById(R.id.btn_register_login);
        btn_login_choose = findViewById(R.id.btn_login_choose);
        btn_register_choose = findViewById(R.id.btn_register_choose);
    }

    private void onClick() {
        btn_login_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_login.setVisibility(View.VISIBLE);
                btn_register.setVisibility(View.VISIBLE);
                edt_email.setVisibility(View.VISIBLE);
                edt_password.setVisibility(View.VISIBLE);
                btn_login_choose.setVisibility(View.GONE);
                btn_register_choose.setVisibility(View.GONE);
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
        btn_register_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edt_email.getText().toString().equals("") && !edt_password.getText().toString().equals("")) {
                    login();
                } else {
                    Toast.makeText(LoginActivity.this, "Email hoặc mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void login() {
        AlertDialog progress = new SpotsDialog(LoginActivity.this, R.style.custom_login);
        progress.show();
        accountDBContext.getAuth().signInWithEmailAndPassword(edt_email.getText().toString(), edt_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    accountDBContext.getReference().addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Account account = dataSnapshot.getValue(Account.class);
                                if (account.getId().equals(accountDBContext.getAuth().getCurrentUser().getUid())) {

                                    if (account != null && account.isAdmin()) {
                                        Common.account = account;
                                        startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                                        finish();
                                        //admin
                                        Toast.makeText(getApplicationContext(), "Đây là admin", Toast.LENGTH_SHORT).show();
                                    } else if (account != null && account.isFirstSetup()) {
                                        Common.account = account;
                                        Toast.makeText(getApplicationContext(), "Đây là lần đầu", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this, UserActivity.class));
                                        finish();
                                        //first setup
                                    } else if (account != null && account.isBlock()) {
                                        Common.account = account;
                                        Toast.makeText(getApplicationContext(), "Tài khoản bị khóa", Toast.LENGTH_SHORT).show();
                                    } else if (account != null) {
                                        //normal account
                                        Common.account = account;
                                        Toast.makeText(getApplicationContext(), "Tài khoản bình thường", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this, UserActivity.class));
                                        finish();
                                    }
                                    loadListAccount();

                                }
                            }
                            progress.dismiss();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                }
                else {
                    progress.dismiss();
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại, kiểm tra lại email hoặc mật khẩu nhé bạn!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void loadListAccount() {
        Common.accountList = new ArrayList<>();
        accountDBContext.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    Account account = dataSnapshot.getValue(Account.class);
                    if(!account.getId().equals(accountDBContext.getAuth().getCurrentUser().getUid())){
                        Common.accountList.add(account);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}