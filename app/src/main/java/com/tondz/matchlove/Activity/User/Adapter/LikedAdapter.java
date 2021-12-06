package com.tondz.matchlove.Activity.User.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.tondz.matchlove.FirebaseContext.AccountDBContext;
import com.tondz.matchlove.FirebaseContext.MatchDBContext;
import com.tondz.matchlove.Model.Account;
import com.tondz.matchlove.Model.Match;
import com.tondz.matchlove.R;

import java.util.List;

public class LikedAdapter extends ArrayAdapter<Match> {
    private Context context;
    private List<Match> matchList;
    private AccountDBContext dbContext;
    private MatchDBContext matchDBContext;

    public LikedAdapter(Context context, List<Match> matchList) {
        super(context, 0, matchList);
        this.matchList = matchList;
        matchDBContext = new MatchDBContext(context);
        dbContext = new AccountDBContext();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_person_liked, parent, false);
        }
        ImageView img_avatar = convertView.findViewById(R.id.img_avatar);
        TextView tv_name = convertView.findViewById(R.id.tv_nameuser);
        Button btn_cancel = convertView.findViewById(R.id.btn_cancel);
        Match match = matchList.get(position);
        Account account= match.getAccount2();
        tv_name.setText(account.getFullName());
        if (!account.getAvatar().equals("")) {
            dbContext.getStorageReference().child(account.getId()).child(account.getAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(getContext()).load(uri).into(img_avatar);
                }
            });
        }
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                btn_cancel.setVisibility(View.VISIBLE);
                return false;
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_cancel.setVisibility(View.GONE);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchDBContext.Delete(match);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
