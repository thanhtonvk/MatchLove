package com.tondz.matchlove.Activity.User;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tondz.matchlove.Activity.User.Adapter.MessageAdapter;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.FirebaseContext.MatchDBContext;
import com.tondz.matchlove.Model.Account;
import com.tondz.matchlove.Model.Match;
import com.tondz.matchlove.R;

import java.util.ArrayList;
import java.util.List;

public class BlockActivity extends AppCompatActivity {

    Button btn_back;
    ListView lv_accountblock;
    MatchDBContext matchDBContext;
    List<Account> accountList;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block);
        initView();
        readUsers();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        btn_back = findViewById(R.id.btn_back);
        lv_accountblock = findViewById(R.id.lv_account_block);
        matchDBContext = new MatchDBContext(BlockActivity.this);
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
                            Common.account.getId().equals(match.getAccount2().getId())) && (match.isIdUser1Accept() && match.isIdUser2Accept()) && match.isBlocked()) {
                        if (!Common.account.getId().equals(match.getAccount1().getId())) {
                            accountList.add(match.getAccount1());
                        }
                        if (!Common.account.getId().equals(match.getAccount2().getId())) {
                            accountList.add(match.getAccount2());
                        }
                    }
                }
                messageAdapter = new MessageAdapter(BlockActivity.this, accountList);
                lv_accountblock.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}