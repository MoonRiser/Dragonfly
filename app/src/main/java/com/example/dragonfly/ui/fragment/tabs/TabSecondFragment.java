package com.example.dragonfly.ui.fragment.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dragonfly.R;
import com.example.dragonfly.adapter.MyGalleryAdapter;
import com.example.dragonfly.transformer.CarouselPageTransformer;
import com.example.dragonfly.transformer.HorizontalStackTransformer;
import com.example.dragonfly.transformer.ScaleOutInPageTransformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabSecondFragment extends Fragment {
    private List<Integer> datas = new ArrayList<>();
    private ViewPager2 viewPagerStack;
    private ViewPager2 viewPagerScale;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tab_second,container,false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        viewPagerScale = view.findViewById(R.id.vp2_scale);
        viewPagerStack = view.findViewById(R.id.vp2_stack);
        datas.addAll(Arrays.asList(R.drawable.image_01, R.drawable.image_02, R.drawable.image_03, R.drawable.play, R.drawable.the_red));

        viewPagerScale.setAdapter(new MyGalleryAdapter(datas));
        viewPagerScale.setOffscreenPageLimit(1);
        viewPagerScale.setPageTransformer(new ScaleOutInPageTransformer());

        viewPagerStack.setAdapter(new MyGalleryAdapter(datas));
        viewPagerStack.setOffscreenPageLimit(3);
        viewPagerStack.setPageTransformer(new HorizontalStackTransformer(viewPagerStack));





    }
}
