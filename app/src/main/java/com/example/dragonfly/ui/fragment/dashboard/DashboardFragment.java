package com.example.dragonfly.ui.fragment.dashboard;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dragonfly.R;
import com.example.dragonfly.adapter.MyGalleryAdapter;
import com.example.dragonfly.api.NetWork;
import com.example.dragonfly.bean.Categories;
import com.example.dragonfly.constant.Constants;
import com.example.dragonfly.global.LiveDataBus;
import com.example.dragonfly.transformer.CarouselPageTransformer;
import com.example.dragonfly.transformer.ScaleOutInPageTransformer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private List<Integer> datas = new ArrayList<>();
    private ViewPager2 viewPager2;
    private boolean isCarousel = false;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        final Button button = view.findViewById(R.id.BTrequst);
        button.setOnClickListener(v -> NetWork.getGankCategories(new NetWork.ResultCallback<Categories>() {
            @Override
            public void onSuccess(Categories result) {
                Log.i("xres", "response已成功接收");
                ((TextView) view.findViewById(R.id.TVresult)).setText(result.toString());
            }

            @Override
            public void onError(String message) {

            }
        }));
        MutableLiveData<String> isClickFast = LiveDataBus.get().with(Constants.CLICK_TOO_FAST, String.class);
        isClickFast.observe(getViewLifecycleOwner(), s -> Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show());
        datas.addAll(Arrays.asList(R.drawable.image_01, R.drawable.image_02, R.drawable.image_03, R.drawable.play, R.drawable.the_red));
        viewPager2 = view.findViewById(R.id.vp2_gallery);
        viewPager2.setAdapter(new MyGalleryAdapter(datas));
        viewPager2.setOffscreenPageLimit(3);
        //   viewPager2.setPageTransformer(new MarginPageTransformer(32));
        viewPager2.setPageTransformer(new CarouselPageTransformer());


    }


}