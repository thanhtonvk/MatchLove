package com.tondz.matchlove.Activity.User.Fragment.FragmentMatch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tondz.matchlove.Activity.User.Adapter.IsLikedAdapter;
import com.tondz.matchlove.Activity.User.Adapter.LikedAdapter;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.FirebaseContext.MatchDBContext;
import com.tondz.matchlove.Model.Match;
import com.tondz.matchlove.R;

import java.util.ArrayList;
import java.util.List;

public class LikedFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_is_liked, container, false);
    }

    ListView lv_liked;
    MatchDBContext matchDBContext;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setLv_isliked();
    }

    private void initView(View view) {
        lv_liked = view.findViewById(R.id.lv_isliked);
        matchDBContext = new MatchDBContext(getContext());
    }

    List<Match> matchList;
    LikedAdapter adapter;

    public void setLv_isliked() {
        matchDBContext.reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                matchList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    Match match = dataSnapshot.getValue(Match.class);
                    if (Common.account.getId().equals(match.getAccount1().getId()) && !match.isIdUser2Accept()) {
                        matchList.add(match);
                    }
                }
                adapter = new LikedAdapter(getContext(), matchList);
                lv_liked.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}