package com.tondz.matchlove.FirebaseContext;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tondz.matchlove.Model.Video;
import com.tondz.matchlove.R;

import dmax.dialog.SpotsDialog;

public class VideoDBContext {
    public DatabaseReference reference;
    public FirebaseDatabase database;

    public VideoDBContext() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Video");
    }

    public void update(Video video, Context context) {
        AlertDialog progress = new SpotsDialog(context, R.style.custom_pleasewait);
        progress.show();
        reference.child(video.getId()).setValue(video).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                } else {
                    Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            }
        });
    }

    public void delete(String id, Context context) {
        AlertDialog progress = new SpotsDialog(context, R.style.custom_pleasewait);
        progress.show();
        reference.child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                } else {
                    Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            }
        });
    }
}
