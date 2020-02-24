package com.example.dragonfly.interceptor;

import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MyInterceptor implements Interceptor {

    private Map<String, Long> map = new HashMap<>();

    @Override
    public Response intercept(Chain chain) throws IOException {


        Long timestamp = System.currentTimeMillis();
        Request request = chain.request();
        String urlstr = request.url().toString();

        if (map.containsKey(urlstr)) {
            if (timestamp - map.get(urlstr) < 1000) {
                Log.i("xres", "你手速太快了");
                chain.call().cancel();
            } else {
                map.put(urlstr, timestamp);
            }
        } else {
            map.put(urlstr, timestamp);
        }

        return chain.proceed(request);

    }
}
