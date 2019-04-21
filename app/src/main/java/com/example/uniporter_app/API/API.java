package com.example.uniporter_app.API;

import com.example.uniporter_app.API_models.PreferenceResponse;
import com.example.uniporter_app.API_models.RegisterResponse;
import com.example.uniporter_app.API_models.LoginResponse;
import com.example.uniporter_app.API_models.RideResponse;
import com.example.uniporter_app.API_models.SharerideResponse;
import com.example.uniporter_app.API_models.UserResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface API {


    @FormUrlEncoded
    @POST("create/")
    Call<RegisterResponse> createUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name
    );

    @FormUrlEncoded
    @POST("token/")
    Call<LoginResponse> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("me/")
    Call<UserResponse> getUser(
            @Header("Authorization") String authHeader
    );

    @GET("rides/")
    Call<List<RideResponse>> getRides(
            @Header("Authorization") String authHeader
    );

    @FormUrlEncoded
    @POST("rides/")
    Call<RideResponse> addRide(
            @Field("user") int user_id,
            @Field("user_email") String user_email,
            @Field("type") String type,
            @Field("airline") String airline,
            @Field("flight_no") String flight_no,
            @Field("date") String date,
            @Field(value = "preferences", encoded = true) List<Integer> preferences,
            @Field(value = "tags", encoded = true) List<Integer> tags,
            @Header("Authorization") String authHeader
    );

    @FormUrlEncoded
    @POST("sharerides")
    Call<List<SharerideResponse>> getShareRides(
            @Field("date") String date
    );

    @FormUrlEncoded
    @POST("preferences/")
    Call<PreferenceResponse> addPreference(
            @Field("name") String name,
            @Field("weight") int weight,
            @Field("wait_time") int wait_time,
            @Field("residence") String residence,
            @Header("Authorization") String authHeader
    );
}
