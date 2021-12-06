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
import android.widget.GridView;

import com.tondz.matchlove.Activity.User.Adapter.AlbumAdapter;
import com.tondz.matchlove.Activity.User.ListMusicActivity;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.Model.Album;
import com.tondz.matchlove.Model.Music;
import com.tondz.matchlove.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MusicFragmentUser extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_music, container, false);
    }

    Random random;
    GridView gridView;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        loadListAlbum();
        random = new Random();
        if(getContext()!=null){
            AlbumAdapter adapter = new AlbumAdapter(getContext(),albumList);
            gridView.setAdapter(adapter);
        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Common.album = albumList.get(i);
                startActivity(new Intent(getContext(), ListMusicActivity.class));
            }
        });

    }
    private void initView(View view){
        gridView = view.findViewById(R.id.gv_album);
    }
    List<Album>albumList;
    private void loadListAlbum(){
        albumList= new ArrayList<>();
        List<Music>musicList = new ArrayList<>();
        musicList.add(new Music("1","https://c1-ex-swe.nixcdn.com/NhacCuaTui993/LaLungLofiVersion-Vu-6181036.mp3?st=GM28C5yp7IE4oHPaQ2Fe7Q&e=1637252574&download=true","Lạ lùng", 123));

        musicList.add(new Music("2","https://c1-ex-swe.nixcdn.com/NhacCuaTui1022/TheLuongLofiVersion-PhucChinh-7096772_hq.mp3?st=M83XAxlCvDuqHBh8UOB_Kw&e=1637501897&download=true","Thê lương-Phức Chinh", 123));
        albumList.add(new Album("Danh sách nhạc lofi","https://mayruaxemay.vn/wp-content/uploads/2021/04/nhac-lofi-la-gi-1.jpg",musicList));
    }
}