package com.example.dragonfly.adapter;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.dragonfly.ui.fragment.TinkerFragment;
import com.example.dragonfly.ui.fragment.tabs.TabSecondFragment;

public class MyFragmentAdapter extends FragmentStateAdapter {
    public MyFragmentAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        Fragment fragment = new TinkerFragment();
        Fragment fragment1 = new TabSecondFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        // args.putInt(DemoObjectFragment.ARG_OBJECT, position + 1);
        fragment.setArguments(args);
        if (position == 1) {
            return fragment1;
        } else {
            return fragment;
        }

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}