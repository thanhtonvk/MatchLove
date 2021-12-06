package com.tondz.matchlove;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.provider.Settings;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tondz.matchlove.Activity.LoginActivity;
import com.tondz.matchlove.Activity.User.UploadImageActivity;
import com.tondz.matchlove.Activity.User.UserActivity;
import com.tondz.matchlove.FirebaseContext.AccountDBContext;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.Model.Account;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ImageView img_logo;
    TextView tv_logo;
    AccountDBContext accountDBContext;

    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    int REQUEST_LOCATION = 111;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        accountDBContext = new AccountDBContext();
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein_animation);
        img_logo.setAnimation(animation);
        tv_logo.setAnimation(animation);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        CountDownTimer countDownTimer = new CountDownTimer(3000, 100) {
            @Override
            public void onTick(long l) {

            }
            @Override
            public void onFinish() {
                if (accountDBContext.getAuth().getCurrentUser() != null) {
                    if (!accountDBContext.getAuth().getCurrentUser().getUid().equals("")) {
                        Toast.makeText(getApplicationContext(), "Đã đăng nhập", Toast.LENGTH_SHORT).show();
                        accountDBContext.getReference().addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    Account account = dataSnapshot.getValue(Account.class);
                                    if (account.getId().equals(accountDBContext.getAuth().getCurrentUser().getUid())) {
                                        if (account != null && account.isAdmin()) {
                                            Common.account = account;
                                            finish();
                                            //admin
                                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                            finish();
                                           ;
                                        } else if (account != null && account.isFirstSetup()) {
                                            startActivity(new Intent(getApplicationContext(), UserActivity.class));
                                            finish();
                                            Common.account = account;

                                            //first setup
                                        } else if (account != null && account.isBlock()) {
                                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                            finish();
                                            Common.account = account;

                                        } else if (account != null) {
                                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                            finish();
                                            //normal account
                                            Common.account = account;

                                            startActivity(new Intent(getApplicationContext(), UserActivity.class));
                                        }

                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                    }
                    loadListAccount();
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }


            }
        };
        countDownTimer.start();

    }
    private void initView() {
        img_logo = findViewById(R.id.img_logo);
        tv_logo = findViewById(R.id.tv_logo);
    }

    private void loadListAccount() {
        Common.accountList = new ArrayList<>();
        accountDBContext.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    Account account = dataSnapshot.getValue(Account.class);
                    if(!account.getId().equals(accountDBContext.getAuth().getCurrentUser().getUid())){
                        Common.accountList.add(account);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //location
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                           Common.yourLatLng = new com.tondz.matchlove.Model.Location(location.getLatitude(),location.getLongitude());
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location location = locationResult.getLastLocation();
            Common.yourLatLng = new com.tondz.matchlove.Model.Location(location.getLatitude(),location.getLongitude());
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }
}