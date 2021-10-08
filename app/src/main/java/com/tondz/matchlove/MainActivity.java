package com.tondz.matchlove;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tondz.matchlove.Activity.LoginActivity;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.FirebaseContext.UserContext;
import com.tondz.matchlove.Model.Account;

public class MainActivity extends AppCompatActivity {


    ImageView img_logo;
    TextView tv_logo;
    UserContext userContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        userContext = new UserContext();
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein_animation);
        img_logo.setAnimation(animation);
        tv_logo.setAnimation(animation);
        CountDownTimer countDownTimer = new CountDownTimer(3000, 100) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                if (!userContext.getAuth().getCurrentUser().getUid().equals("")) {
                    Toast.makeText(getApplicationContext(), "Đã đăng nhập", Toast.LENGTH_SHORT).show();
                    userContext.getReference().addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Account account = dataSnapshot.getValue(Account.class);
                                if (account.getId().equals(userContext.getAuth().getCurrentUser().getUid())) {
                                    if (account != null && account.isAdmin()) {
                                        Common.account = account;
                                        finish();
                                        //admin
                                        Toast.makeText(getApplicationContext(), "Đây là admin", Toast.LENGTH_SHORT).show();
                                    } else if (account != null && account.isFirstSetup()) {

                                        Common.account = account;
                                        Toast.makeText(getApplicationContext(), "Đây là lần đầu", Toast.LENGTH_SHORT).show();
                                        //first setup
                                    } else if (account != null && account.isBlock()) {
                                        Common.account = account;
                                        Toast.makeText(getApplicationContext(), "Tài khoản bị khóa", Toast.LENGTH_SHORT).show();
                                    } else if (account != null) {
                                        //normal account
                                        Common.account = account;
                                        Toast.makeText(getApplicationContext(), "Tài khoản bình thường", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }

            }
        };
        countDownTimer.start();

    }

    private void initView() {
        img_logo = findViewById(R.id.img_logo);
        tv_logo = findViewById(R.id.tv_logo);
    }
}