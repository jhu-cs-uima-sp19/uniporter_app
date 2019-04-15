package com.example.uniporter_app.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientSharerides {
    private static final String BASE_URL = "http://ec2-18-212-1-85.compute-1.amazonaws.com:5000/api/";
    private static RetrofitClientSharerides mInstance;
    private Retrofit retrofit;

    private RetrofitClientSharerides() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClientSharerides getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClientSharerides();
        }
        return mInstance;
    }

    public API getAPI() {
        return retrofit.create(API.class);
    }
}
