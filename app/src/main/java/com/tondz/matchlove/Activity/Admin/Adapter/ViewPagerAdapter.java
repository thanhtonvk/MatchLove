package com.tondz.matchlove.Activity.Admin.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tondz.matchlove.Activity.Admin.Fragment.MusicFragment;
import com.tondz.matchlove.Activity.Admin.Fragment.UserFragment;
import com.tondz.matchlove.Activity.Admin.Fragment.VideoFragment;
import com.tondz.matchlove.Activity.User.Fragment.AccountFragment;
import com.tondz.matchlove.Activity.User.Fragment.MainFragment;
import com.tondz.matchlove.Activity.User.Fragment.MatchFragment;
import com.tondz.matchlove.Activity.User.Fragment.MessageFragment;
import com.tondz.matchlove.Activity.User.Fragment.SearchFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new UserFragment();
            case 1:
                return new VideoFragment();
            case 2:
                return new MusicFragment();
            default:
                return new UserFragment();
        }
    }
    @Override
    public int getCount() {
        return 3;
    }
}
