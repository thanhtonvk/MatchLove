package com.tondz.matchlove.Activity.User.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.tondz.matchlove.FirebaseContext.AccountDBContext;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.R;

import java.util.List;

public class ListImageAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> listImages;
    private AccountDBContext dbContext;

    public ListImageAdapter(Context context, List<String> listImages) {
        super(context, 0, listImages);
        this.listImages = listImages;
        dbContext = new AccountDBContext();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image, parent, false);
        }
        String img = listImages.get(position);
        ImageView imageView = convertView.findViewById(R.id.img_slider);
        if (!img.equals("")) {
            dbContext.getStorageReference().child(Common.account.getId()).child(img).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(getContext()).load(uri).into(imageView);
                }
            });
        }
        return convertView;
    }
}
