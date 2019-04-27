package com.example.uniporter_app.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientUser {
    private static final String BASE_URL = "http://ec2-13-59-37-222.us-east-2.compute.amazonaws.com:8000/api/user/";
    private static RetrofitClientUser mInstance;
    private Retrofit retrofit;

    private RetrofitClientUser() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClientUser getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClientUser();
        }
        return mInstance;
    }

    public API getAPI() {
        return retrofit.create(API.class);
    }
}