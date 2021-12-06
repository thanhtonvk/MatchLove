package com.tondz.matchlove.Activity.User.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tondz.matchlove.Activity.User.Fragment.FragmentMatch.IsLikedFragment;
import com.tondz.matchlove.Activity.User.Fragment.FragmentMatch.LikedFragment;
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
            default:
                return new MusicFragmentUser();
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
                return "Nhạc";
            case 1:
                return "Video";
            default:
                return "Nhạc";
        }
    }
}
