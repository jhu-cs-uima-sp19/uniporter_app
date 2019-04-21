package com.example.uniporter_app.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientPreferences {
    private static final String BASE_URL = "http://ec2-54-210-165-217.compute-1.amazonaws.com:8000/api/ride/";
    private static RetrofitClientPreferences mInstance;
    private Retrofit retrofit;

    private RetrofitClientPreferences() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClientPreferences getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClientPreferences();
        }
        return mInstance;
    }

    public API getAPI() {
        return retrofit.create(API.class);
    }
}
