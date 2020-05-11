package com.example.dragonfly.ui.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dragonfly.R;
import com.example.dragonfly.adapter.MyFragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private MyFragmentAdapter fragmentAdapter;
    private ViewPager2 viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        fragmentAdapter = new MyFragmentAdapter(this);
        viewPager = view.findViewById(R.id.VP1);
        viewPager.setUserInputEnabled(false);
        viewPager.setAdapter(fragmentAdapter);
        TabLayout tabLayout = view.findViewById(R.id.TAB);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("Title" + (position + 1))
        ).attach();

    }
}