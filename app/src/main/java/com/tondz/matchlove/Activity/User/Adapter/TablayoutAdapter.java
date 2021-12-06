package com.tondz.matchlove.Activity.User.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tondz.matchlove.Activity.User.Fragment.FragmentMatch.IsLikedFragment;
import com.tondz.matchlove.Activity.User.Fragment.FragmentMatch.LikedFragment;

public class TablayoutAdapter extends FragmentStatePagerAdapter {
    public TablayoutAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new IsLikedFragment();
            case 1:
                return new LikedFragment();
            default:
                return new IsLikedFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Được like";
            case 1:
                return "Đã like";
            default:
                return "Được like";
        }
    }
}
