package com.tondz.matchlove.Activity.User;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tondz.matchlove.Activity.LoginActivity;
import com.tondz.matchlove.FirebaseContext.AccountDBContext;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.R;

public class SettingActivity extends AppCompatActivity {

    TextView btn_changepassword, tv_space;
    SeekBar seekBar_space;
    CheckBox cb_nu, cb_nam;
    EditText edt_fromage, edt_toage;
    TextView btn_block, btn_help;
    Button btn_logout, btn_remove, btn_back, btn_save;
    AccountDBContext accountDBContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        onClick();
        startLoad();

    }

    private void initView() {
        btn_changepassword = findViewById(R.id.btn_setpassword);
        tv_space = findViewById(R.id.tv_space);
        seekBar_space = findViewById(R.id.seekbar_space);
        cb_nu = findViewById(R.id.cb_nu);
        cb_nam = findViewById(R.id.cb_nam);
        edt_fromage = findViewById(R.id.from_age);
        edt_toage = findViewById(R.id.edt_toage);
        btn_block = findViewById(R.id.btn_block);
        btn_help = findViewById(R.id.btn_help);
        btn_logout = findViewById(R.id.btn_logout);
        btn_remove = findViewById(R.id.btn_removeaccount);
        btn_back = findViewById(R.id.btn_back);
        btn_save = findViewById(R.id.btn_save);
        accountDBContext = new AccountDBContext();

    }

    private void startLoad() {

        tv_space.setText(Common.account.getSpace() + " km");
        seekBar_space.setProgress(Common.account.getSpace());
        seekBar_space.setMax(100);
        if (Common.account.isFemale()) {
            cb_nu.setChecked(true);
        }
        if (Common.account.isMale()) {
            cb_nam.setChecked(true);
        }

        edt_fromage.setText(Common.account.getAgefrom()+"");
        edt_toage.setText(Common.account.getAgeto()+"");
    }

    private void onClick() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, ChangePasswordActivity.class));
            }
        });
        seekBar_space.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Common.account.setSpace(i);
                tv_space.setText(Common.account.getSpace() + " km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb_nam.isChecked()) {
                    Common.account.setMale(true);
                }
                if (cb_nu.isChecked()) {
                    Common.account.setFemale(true);
                }
                if(!cb_nam.isChecked()){
                    Common.account.setMale(false);
                }
                if(!cb_nu.isChecked()){
                    Common.account.setFemale(false);
                }
                Common.account.setAgefrom(Integer.parseInt(edt_fromage.getText().toString()));
                Common.account.setAgeto(Integer.parseInt(edt_toage.getText().toString()));
                accountDBContext.update(Common.account,SettingActivity.this);
                setDialog("Thành công");
            }
        });
        btn_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),BlockActivity.class));
            }
        });
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountDBContext.getAuth().signOut();
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
            }
        });
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void setDialog(String content){
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setTitle(content);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}