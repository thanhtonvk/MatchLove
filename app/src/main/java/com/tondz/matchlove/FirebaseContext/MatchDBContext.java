package com.tondz.matchlove.FirebaseContext;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tondz.matchlove.Model.Match;

public class MatchDBContext {
    public FirebaseDatabase database;
    public DatabaseReference reference;
    Context context;

    public MatchDBContext(Context context) {
        this.context = context;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Match");
    }

    public void Update(Match match) {
        reference.child(match.getId()).setValue(match).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (match.isIdUser1Accept()) {
                        Toast.makeText(context, "Đã match", Toast.LENGTH_SHORT).show();
                    }
                    if (match.isIdUser2Accept() && match.isIdUser1Accept()) {
                        Toast.makeText(context, "Đã đồng ý", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void Delete(Match match) {
        reference.child(match.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context,"Đã xóa",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
