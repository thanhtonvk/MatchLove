package com.tondz.matchlove.Activity.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.tondz.matchlove.FirebaseContext.MusicDBContext;
import com.tondz.matchlove.Model.Music;
import com.tondz.matchlove.R;

import java.util.ArrayList;
import java.util.List;

public class MusicListAdapter extends ArrayAdapter<Music> {
    public static List<Music> musicList;
    MusicDBContext dbContext;

    public MusicListAdapter(@NonNull Context context, @NonNull List<Music> objects) {
        super(context, 0, objects);
        this.musicList = objects;
        dbContext = new MusicDBContext();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_lv_music, parent, false
            );
            TextView tv_name = convertView.findViewById(R.id.tv_namemusic);
            TextView tv_views = convertView.findViewById(R.id.tv_views);
            Music music = getItem(position);
            if (music != null) {
                tv_name.setText(music.getName());
                tv_views.setText(music.getView() + " lượt nghe");
            }
        }

        return convertView;
    }


    @NonNull
    @Override
    public Filter getFilter() {
        return userFilter;
    }

    private Filter userFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            List<Music> suggestions = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                suggestions.addAll(musicList);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Music item : musicList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();
            return results;
        }


        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            clear();
            addAll((List) filterResults.values);
            notifyDataSetChanged();


        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Music) resultValue).getName();
        }
    };
}
