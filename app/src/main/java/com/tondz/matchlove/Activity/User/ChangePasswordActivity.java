package com.tondz.matchlove.Activity.User;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.tondz.matchlove.FirebaseContext.AccountDBContext;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.R;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText edt_oldpass, edt_newpass, edt_prenewpass;
    Button btn_change, btn_back;
    AccountDBContext accountDBContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
    }

    private void initView() {
        accountDBContext = new AccountDBContext();
        edt_oldpass = findViewById(R.id.edt_oldpassword);
        edt_newpass = findViewById(R.id.edt_newpassword);
        edt_prenewpass = findViewById(R.id.edt_prenewpassword);
        btn_change = findViewById(R.id.btn_change);
        btn_back = findViewById(R.id.btn_back);
    }

    private void changePassword() {
        if(checkOldPassword()){
            if(checkNewPassword()){
                Common.account.setPassWord(edt_newpass.getText().toString());
                accountDBContext.update(Common.account,ChangePasswordActivity.this);
                finish();
            }
            else{
                setDialog("Mật khẩu không trùng nhau");
            }
        }else{
            setDialog("Mật khẩu cũng không chính xác!!!");
        }
    }
    private void setDialog(String content){
        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
        builder.setTitle(content);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
    private boolean checkOldPassword() {
        if (edt_oldpass.getText().toString().equals(Common.account.getPassWord())) {
            return true;
        } else return false;
    }
    private boolean checkNewPassword(){
        if(edt_newpass.getText().toString().equals(edt_prenewpass.getText().toString())){
            return true;
        }else return false;
    }
}