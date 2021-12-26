package com.tondz.matchlove.Activity.User.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tondz.matchlove.Activity.User.Fragment.FragmentSearch.MapsFragment;
import com.tondz.matchlove.Activity.User.Fragment.FragmentSearch.MusicFragmentUser;
import com.tondz.matchlove.Activity.User.Fragment.FragmentSearch.VideoFragmentUser;

public class SearchAdapter extends FragmentStatePagerAdapter {
    public SearchAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MusicFragmentUser();
            case 1:
                return new VideoFragmentUser();
            case 2:
                return new MapsFragment();
            default:
                return new MusicFragmentUser();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Nhạc";
            case 1:
                return "Video";
            case 2:
                return "Khám phá";
            default:
                return "Nhạc";
        }
    }
}
