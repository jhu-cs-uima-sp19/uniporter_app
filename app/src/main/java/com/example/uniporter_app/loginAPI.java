package com.example.uniporter_app;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface loginAPI {
    @GET("core/user")
    Call<loginResponse[]> getList(@Query("Email") String login_email, @Query("Password") String login_password);
}
