package com.tondz.matchlove.Activity.User.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

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
                return new MainFragment();
            case 1:
                return new SearchFragment();
            case 2:
                return new MatchFragment();
            case 3:
                return new MessageFragment();
            case 4:
                return new AccountFragment();
            default:
                return new MainFragment();
        }

    }

    @Override
    public int getCount() {
        return 5;
    }
}
