package com.tondz.matchlove.Activity.User.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tondz.matchlove.Activity.User.Adapter.MessageAdapter;
import com.tondz.matchlove.Activity.User.ChatActivity;
import com.tondz.matchlove.FirebaseContext.AccountDBContext;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.FirebaseContext.MatchDBContext;
import com.tondz.matchlove.Model.Account;
import com.tondz.matchlove.Model.Match;
import com.tondz.matchlove.R;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    AutoCompleteTextView autoCompleteTextView;
    ListView lv;
    List<Account> accountList;
    AccountDBContext accountDBContext;
    MatchDBContext matchDBContext;
    MessageAdapter messageAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        accountDBContext = new AccountDBContext();
        readUsers();
        MessageAdapter messageAdapter = new MessageAdapter(getContext(), accountList);
        lv.setAdapter(messageAdapter);
        autoCompleteTextView.setAdapter(messageAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Common.AccountChat = accountList.get(i);
                String userID = accountList.get(i).getId();
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView(View view) {
        autoCompleteTextView = view.findViewById(R.id.autoComplete_message);
        lv = view.findViewById(R.id.lv_message);
        matchDBContext = new MatchDBContext(getContext());
        accountList = new ArrayList<>();
    }



    private void readUsers() {
        matchDBContext.reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                accountList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    Match match = dataSnapshot.getValue(Match.class);
                    if ((Common.account.getId().equals(match.getAccount1().getId()) ||
                            Common.account.getId().equals(match.getAccount2().getId())) && (match.isIdUser1Accept() && match.isIdUser2Accept())&&!match.isBlocked()) {
                        if (!Common.account.getId().equals(match.getAccount1().getId())) {
                            accountList.add(match.getAccount1());
                        }
                        if (!Common.account.getId().equals(match.getAccount2().getId())) {
                            accountList.add(match.getAccount2());
                        }
                    }
                }
                if(getContext()!=null){
                    messageAdapter = new MessageAdapter(getContext(), accountList);
                    lv.setAdapter(messageAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}