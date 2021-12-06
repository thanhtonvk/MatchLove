package com.tondz.matchlove.Activity.User.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.tondz.matchlove.FirebaseContext.AccountDBContext;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.R;

import java.util.List;

public class PhotoAdapter extends PagerAdapter {

    private Context context;
    private List<String> mListPhoto;
    AccountDBContext dbContext;

    public PhotoAdapter(Context context, List<String> mListPhoto) {
        this.context = context;
        this.mListPhoto = mListPhoto;
        dbContext = new AccountDBContext();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_slider_photo, container, false);
        ImageView img = view.findViewById(R.id.img_slider);
        String string = mListPhoto.get(position);
        if(!string.equals("")){
            dbContext.getStorageReference().child(Common.accountList.get(Common.indexAccount).getId()).child(string).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(context).load(uri).into(img);
                }
            });
        }
        //add view to viewgroup
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if (mListPhoto != null) {
           return mListPhoto.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
