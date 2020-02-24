package com.example.dragonfly.api;

import com.example.dragonfly.bean.Categories;
import com.example.dragonfly.interceptor.MyInterceptor;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWork {
    private static GankApi sGankApi;

    private static final String BASE_URL = "https://gank.io/api/";

    private static GankApi getGankApi() {
        if (sGankApi == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .addInterceptor(new MyInterceptor());


            sGankApi = new Retrofit.Builder()
                    .client(builder.build())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(GankApi.class);
        }
        return sGankApi;
    }

    //异步操作方式
    public static void getGankCategories(final ResultCallback<Categories> resultCallback) {
        Call<Categories> gankCategories = getGankApi().getCategories();
        gankCategories.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                Categories body = response.body();
                if (body == null) {
                    resultCallback.onError("body is null!");
                    return;
                }
                resultCallback.onSuccess(body);
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                resultCallback.onError(t.getMessage());
            }
        });
    }

    //同步操作方式
    public static Categories getGankCategoriesSync() throws IOException {
        Call<Categories> gankCategories = getGankApi().getCategories();
        return gankCategories.execute().body();
    }

    public interface ResultCallback<T> {
        void onSuccess(T result);

        void onError(String message);
    }
}