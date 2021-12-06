package com.tondz.matchlove.Activity.Admin.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tondz.matchlove.Activity.Admin.Adapter.AccountListAdapter;
import com.tondz.matchlove.FirebaseContext.AccountDBContext;
import com.tondz.matchlove.Model.Account;
import com.tondz.matchlove.R;

import java.util.ArrayList;
import java.util.List;


public class UserFragment extends Fragment {

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    AutoCompleteTextView edt_search;
    ListView lv_account;
    AccountDBContext dbContext;
    List<Account> accountList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        loadListAccount();
        lv_account.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               setDialog(accountList.get(i));
            }
        });
    }

    private void setDialog(Account account) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Tùy chọn");

        builder.setPositiveButton("Khóa tài khoản", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dbContext.block(account, getContext());
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Mở tải khoản", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dbContext.unlock(account, getContext());
            }
        });
        builder.show();
    }

    private void initView(View view) {
        edt_search = view.findViewById(R.id.edt_search);
        lv_account = view.findViewById(R.id.lv_account);
        dbContext = new AccountDBContext();
        accountList = new ArrayList<>();
    }

    private void loadListAccount() {
        accountList.clear();
        dbContext.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    Account account = dataSnapshot.getValue(Account.class);
                    accountList.add(account);
                }
                AccountListAdapter adapter = new AccountListAdapter(getContext(), accountList);
                lv_account.setAdapter(adapter);
                edt_search.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}