package com.tondz.matchlove.Activity.User.Fragment.FragmentSearch;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tondz.matchlove.Activity.Admin.Adapter.VideoListAdapter;
import com.tondz.matchlove.Activity.Admin.InputVideoActivity;
import com.tondz.matchlove.Activity.User.PlayVideoActivity;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.FirebaseContext.VideoDBContext;
import com.tondz.matchlove.Model.Video;
import com.tondz.matchlove.R;

import java.util.ArrayList;
import java.util.List;


public class VideoFragmentUser extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_user, container, false);
    }
    AutoCompleteTextView edt_Search;
    ListView lv_ListVideo;
    VideoDBContext dbContext;
    List<Video> videoList;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        loadListVideo();
        lv_ListVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Common.video = videoList.get(i);
                startActivity(new Intent(getContext(), PlayVideoActivity.class));
            }
        });
    }
    private void initView(View view){
        edt_Search = view.findViewById(R.id.edt_search);
        lv_ListVideo = view.findViewById(R.id.lv_video);
        dbContext = new VideoDBContext();
        videoList = new ArrayList<>();
    }
    private void loadListVideo(){
        dbContext.reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()
                ) {
                    Video video = dataSnapshot.getValue(Video.class);
                    videoList.add(video);
                }
                if(getContext()!=null){
                    VideoListAdapter adapter = new VideoListAdapter(getContext(),videoList);
                    lv_ListVideo.setAdapter(adapter);
                    edt_Search.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}