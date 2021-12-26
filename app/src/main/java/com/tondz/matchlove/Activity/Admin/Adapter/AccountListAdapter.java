package com.tondz.matchlove.Activity.Admin.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.tondz.matchlove.FirebaseContext.AccountDBContext;
import com.tondz.matchlove.Model.Account;
import com.tondz.matchlove.R;

import java.util.ArrayList;
import java.util.List;

public class AccountListAdapter extends ArrayAdapter<Account> {
    public static List<Account> accountList;
    AccountDBContext dbContext;
    public AccountListAdapter(@NonNull Context context, @NonNull List<Account> objects) {
        super(context, 0, objects);
        this.accountList = objects;
        dbContext = new AccountDBContext();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_lv_account, parent, false
            );
            TextView tv_name = convertView.findViewById(R.id.tv_fullname);
            TextView tv_id = convertView.findViewById(R.id.tv_id);
            ImageView img_avatar = convertView.findViewById(R.id.img_avatar);
            Account account = getItem(position);
            if (account != null) {
                tv_name.setText(account.getFullName());
                tv_id.setText("Tình trạng: "+account.isBlock());
                if(!account.getAvatar().equals("")){
                    dbContext.getStorageReference().child(account.getId()).child(account.getAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(getContext()).load(uri).into(img_avatar);
                        }
                    });
                }
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
            List<Account> suggestions = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                suggestions.addAll(accountList);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Account item : accountList) {
                    if (item.getFullName().toLowerCase().contains(filterPattern)||item.getId().toLowerCase().contains(filterPattern)) {
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
