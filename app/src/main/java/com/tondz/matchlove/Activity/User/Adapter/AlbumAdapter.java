package com.tondz.matchlove.Activity.User.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.tondz.matchlove.Model.Album;
import com.tondz.matchlove.R;

import java.util.List;

public class AlbumAdapter extends ArrayAdapter<Album> {
    List<Album> albumList;
    Context context;

    public AlbumAdapter(Context context, List<Album> albumList) {
        super(context, 0, albumList);
        this.albumList = albumList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_album, parent, false);
        }
        ImageView imageView = convertView.findViewById(R.id.image_view);
        TextView tv_name = convertView.findViewById(R.id.tv_namealbum);
        Album album = albumList.get(position);
        Glide.with(context).load(album.getImage()).into(imageView);
        tv_name.setText(album.getName());
        return convertView;
    }
}
