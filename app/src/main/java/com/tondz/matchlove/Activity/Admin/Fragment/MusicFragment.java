package com.tondz.matchlove.Activity.Admin.Fragment;

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
import com.tondz.matchlove.Activity.Admin.Adapter.MusicListAdapter;
import com.tondz.matchlove.Activity.Admin.InputMusicActivity;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.FirebaseContext.MusicDBContext;
import com.tondz.matchlove.FirebaseContext.MusicDBContext;
import com.tondz.matchlove.Model.Music;
import com.tondz.matchlove.Model.Music;
import com.tondz.matchlove.R;

import java.util.ArrayList;
import java.util.List;

public class MusicFragment extends Fragment {


    AutoCompleteTextView edt_Search;
    ImageButton btn_Add;
    ListView lv_ListMusic;
    MusicDBContext dbContext;
    List<Music> musicList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        loadListMusic();
        lv_ListMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Common.music = musicList.get(i);
                startActivity(new Intent(getContext(), InputMusicActivity.class));
                musicList.clear();
            }
        });
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicList.clear();
                startActivity(new Intent(getContext(),InputMusicActivity.class));
            }
        });
    }

    private void initView(View view){
        edt_Search = view.findViewById(R.id.edt_search);
        btn_Add = view.findViewById(R.id.btn_add);
        lv_ListMusic = view.findViewById(R.id.lv_music);
        dbContext = new MusicDBContext();
        musicList = new ArrayList<>();
    }
    private void loadListMusic(){
        musicList.clear();
        dbContext.reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()
                ) {
                    Music music = dataSnapshot.getValue(Music.class);
                    musicList.add(music);
                }
                MusicListAdapter adapter = new MusicListAdapter(getContext(),musicList);
                lv_ListMusic.setAdapter(adapter);
                edt_Search.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}