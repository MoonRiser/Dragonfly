package com.example.dragonfly.ui.fragment.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.dragonfly.R;
import com.example.dragonfly.api.NetWork;
import com.example.dragonfly.bean.Categories;
import com.example.dragonfly.constant.Constants;
import com.example.dragonfly.global.LiveDataBus;


public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final Button button = root.findViewById(R.id.BTrequst);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetWork.getGankCategories(new NetWork.ResultCallback<Categories>() {
                    @Override
                    public void onSuccess(Categories result) {
                        Log.i("xres", "response已成功接收");
                        ((TextView) root.findViewById(R.id.TVresult)).setText(result.toString());
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            }
        });


        MutableLiveData<Boolean> isClickFast = LiveDataBus.get().with(Constants.CLICK_TOO_FAST,Boolean.TYPE);
        isClickFast.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(getActivity(), "你手速太快了"+aBoolean , Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
}