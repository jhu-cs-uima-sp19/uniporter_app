package com.example.uniporter_app;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterAPI {


    @FormUrlEncoded
    @POST("create/")
    Call<RegisterResponse> createUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name
    );
}
