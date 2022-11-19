package com.cit.i_chat.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cit.i_chat.fagment.CallFragment;
import com.cit.i_chat.fagment.ChatFragment;
import com.cit.i_chat.fagment.UserFragment;

public class FragmentAdapter extends FragmentPagerAdapter {

    String[] name = {"User", "Chat", "Call"};

    public FragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new UserFragment();
            case 1:
                return new ChatFragment();
            case 2:
                return  new CallFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return name[position];
    }
}
