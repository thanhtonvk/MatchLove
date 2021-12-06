package com.tondz.matchlove.Activity.User.Adapter;

import android.content.Context;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.tondz.matchlove.FirebaseContext.AccountDBContext;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.Model.Account;
import com.tondz.matchlove.R;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends ArrayAdapter<Account> {

    Context context;
    List<Account>accountList;
    AccountDBContext dbContext;
    public MessageAdapter(@NonNull Context context, @NonNull List<Account> accountList) {
        super(context, 0, accountList);
        this.accountList = accountList;
        this.context = context;
        dbContext = new AccountDBContext();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_lv_message, parent, false
            );
        }
        ImageView img = convertView.findViewById(R.id.img_avatar_user);
        TextView tv_name = convertView.findViewById(R.id.tv_nameuser);
        Account account = accountList.get(position);
        tv_name.setText(account.getFullName());
        if(!account.getAvatar().equals("")){
            dbContext.getStorageReference().child(account.getId()).child(account.getAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(context).load(uri).into(img);
                }
            });
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
            List<Account> suggestions = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                suggestions.addAll(accountList);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Account item : accountList) {
                    if (item.getFullName().toLowerCase().contains(filterPattern)) {
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
            return ((Account) resultValue).getFullName();
        }
    };

}
