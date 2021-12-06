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
import com.tondz.matchlove.FirebaseContext.VideoDBContext;
import com.tondz.matchlove.Model.Video;
import com.tondz.matchlove.R;

import java.util.ArrayList;
import java.util.List;

public class VideoListAdapter extends ArrayAdapter<Video> {
    public static List<Video> videoList;
    VideoDBContext dbContext;
    public VideoListAdapter(@NonNull Context context, @NonNull List<Video> objects) {
        super(context, 0, objects);
        this.videoList = objects;
        dbContext = new VideoDBContext();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_lv_video, parent, false
            );
            TextView tv_name = convertView.findViewById(R.id.tv_namevideo);
            TextView tv_views = convertView.findViewById(R.id.tv_views);
            ImageView img_thumb = convertView.findViewById(R.id.img_thumb);
            Video video = getItem(position);
            if (video != null) {
                tv_name.setText(video.getName());
                Glide.with(getContext()).load(String.format("https://img.youtube.com/vi/%s/default.jpg",video.getId())).into(img_thumb);
                tv_views.setText(video.getViews()+" lượt xem");
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
            List<Video> suggestions = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                suggestions.addAll(videoList);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Video item : videoList) {
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
            return ((Video) resultValue).getName();
        }
    };
}
