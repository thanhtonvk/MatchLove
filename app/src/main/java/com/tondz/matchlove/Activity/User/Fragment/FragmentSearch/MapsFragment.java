package com.tondz.matchlove.Activity.User.Fragment.FragmentSearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tondz.matchlove.Activity.User.ViewProfileActivity;
import com.tondz.matchlove.FirebaseContext.AccountDBContext;
import com.tondz.matchlove.FirebaseContext.Common;
import com.tondz.matchlove.Model.Account;
import com.tondz.matchlove.R;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    ViewGroup viewGroup;
    GoogleMap mMap;
    AccountDBContext dbContext;
    SupportMapFragment mapFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        dbContext = new AccountDBContext();
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_maps, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        return viewGroup;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMyLocationEnabled(true);
        onLoadAccount();

    }

    public void onLoadAccount() {
        if (Common.accountList != null) {
            for (Account account : Common.accountList
            ) {
                if (account.getLocation() != null) {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(new LatLng(account.getLocation().getLatitude(), account.getLocation().getLongtitude()))
                            .title(account.getFullName());
                    mMap.addMarker(markerOptions);
                }
            }
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Common.AccountChat = getAccount(marker);
                startActivity(new Intent(getContext(), ViewProfileActivity.class));
                return false;
            }
        });
    }

    public Account getAccount(Marker marker) {
        Account account = null;
        for (Account ac : Common.accountList
        ) {
            if(ac.getLocation()!=null){
                if (ac.getLocation().getLatitude() == marker.getPosition().latitude && ac.getLocation().getLongtitude() == marker.getPosition().longitude) {
                    account = ac;
                }
            }

        }
        return account;
    }
}