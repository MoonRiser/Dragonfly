package com.example.dragonfly.interceptor;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.dragonfly.constant.Constants;
import com.example.dragonfly.global.LiveDataBus;
import com.example.dragonfly.ui.fragment.dashboard.DashboardFragment;
import com.example.dragonfly.ui.fragment.dashboard.DashboardViewModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MyInterceptor implements Interceptor {

    private Map<String, Long> map = new HashMap<>();

    @Override
    public Response intercept(Chain chain) throws IOException {


        Long timestamp = System.currentTimeMillis();
        Request request = chain.request();
        Response response = chain.proceed(request);
        String urlstr = request.url().toString();
//        ViewModel viewModel = ViewModelProviders.of(new DashboardFragment()).get(DashboardViewModel.class);

        if (map.containsKey(urlstr)) {
            if (timestamp - map.get(urlstr) < 1000) {
                Log.i("xres", "你手速太快了");
                LiveDataBus.get().with(Constants.CLICK_TOO_FAST,Boolean.TYPE).postValue(true);
                chain.call().cancel();

            } else {
                map.put(urlstr, timestamp);
            }
        } else {
            map.put(urlstr, timestamp);
        }

        return response;

    }
}
