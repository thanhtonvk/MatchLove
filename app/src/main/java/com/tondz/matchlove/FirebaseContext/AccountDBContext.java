package com.tondz.matchlove.FirebaseContext;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tondz.matchlove.Model.Account;
import com.tondz.matchlove.R;

import dmax.dialog.SpotsDialog;

public class AccountDBContext {
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;


    public AccountDBContext() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("User");
        auth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("Image");
    }


    public void uploadImage(Uri uri, Account account, int index, Context context) {
        storageReference = storageReference.child(account.getId()).child(uri.getLastPathSegment());
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String path = uri.getPath().split("/")[uri.getPath().split("/").length-1];
                account.getImages().set(index,path);
                update(account,context);
            }
        });
    }


    public void update(Account account, Context context) {
        AlertDialog progress = new SpotsDialog(context, R.style.custom_pleasewait);
        progress.show();
        reference.child(account.getId()).setValue(account).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progress.dismiss();
                } else {
                    Toast.makeText(context, "Không thành công", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            }
        });
    }

    public void block(Account account, Context context) {
        AlertDialog progress = new SpotsDialog(context, R.style.custom_pleasewait);
        progress.show();
        account.setBlock(true);
        reference.child(account.getId()).setValue(account).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                } else {
                    Toast.makeText(context, "Không thành công", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            }
        });
    }

    public void unlock(Account account, Context context) {
        AlertDialog progress = new SpotsDialog(context, R.style.custom_pleasewait);
        progress.show();
        account.setBlock(false);
        reference.child(account.getId()).setValue(account).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                } else {
                    Toast.makeText(context, "Không thành công", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            }
        });
    }
    public StorageReference getStorageReference() {
        return storageReference;
    }

    public void setStorageReference(StorageReference storageReference) {
        this.storageReference = storageReference;
    }

    public FirebaseStorage getFirebaseStorage() {
        return firebaseStorage;
    }

    public void setFirebaseStorage(FirebaseStorage firebaseStorage) {
        this.firebaseStorage = firebaseStorage;
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public void setAuth(FirebaseAuth auth) {
        this.auth = auth;
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public void setDatabase(FirebaseDatabase database) {
        this.database = database;
    }

    public DatabaseReference getReference() {
        return reference;
    }

    public void setReference(DatabaseReference reference) {
        this.reference = reference;
    }


}
