package com.tondz.matchlove.Activity.User.Fragment;

import android.content.Intent;
import android.icu.text.IDNA;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.tondz.matchlove.Activity.User.EditInforActivity;
import com.tondz.matchlove.Activity.User.InfomationActivity;
import com.tondz.matchlove.Activity.User.SettingActivity;
import com.tondz.matchlove.Activity.User.UploadImageActivity;
import com.tondz.matchlove.FirebaseContext.AccountDBContext;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.R;

public class AccountFragment extends Fragment {

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }


    Button btn_setting;
    ImageButton btn_addImage;
    Button btn_safe;
    ImageView img_avatar, img_info;
    TextView tv_name, tv_age, tv_address;
    AccountDBContext dbContext;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        onClick();
        setInformation();
    }

    private void initView(View view) {
        btn_setting = view.findViewById(R.id.btn_setting);
        btn_addImage = view.findViewById(R.id.btn_media);
        btn_safe = view.findViewById(R.id.btn_safety);
        img_avatar = view.findViewById(R.id.img_avatar);
        tv_name = view.findViewById(R.id.tv_name_account);
        tv_age = view.findViewById(R.id.tv_age_account);
        tv_address = view.findViewById(R.id.tv_address_account);
        img_info = view.findViewById(R.id.img_info);
        dbContext = new AccountDBContext();
    }

    private void onClick() {
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SettingActivity.class));
            }
        });
        img_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), InfomationActivity.class));
            }
        });
        img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), EditInforActivity.class));
            }
        });
        btn_addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), UploadImageActivity.class));
            }
        });

    }

    private void setInformation() {
        if (Common.account != null) {
            if (!Common.account.getAvatar().equals("")) {
                dbContext.getStorageReference().child(Common.account.getId()).child(Common.account.getAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if(getContext()!=null){
                            Glide.with(getContext()).load(uri).into(img_avatar);
                        }

                    }
                });
            }
            tv_name.setText(Common.account.getFullName());
            int yearNow = java.time.LocalDate.now().getYear();
            int yearUser =Integer.parseInt(Common.account.getDateOfBirth().split("-")[Common.account.getDateOfBirth().split("-").length-1].trim());
            tv_age.setText(yearNow-yearUser+" tuổi");
            if(Common.account.getAddress()!=null){
                tv_address.setText(Common.account.getAddress().split(",")[Common.account.getAddress().split(",").length-1]);
            }

        }
    }

}