package com.example.dragonfly.api;

import com.example.dragonfly.bean.Categories;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GankApi {
    //完整url: https://gank.io/api/xiandu/categories ，https://gank.io/api/作为baseUrl
    @GET("xiandu/categories")
    Call<Categories> getCategories();
}