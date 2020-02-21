package com.example.gambungstore.services;

import com.example.gambungstore.models.Login;
import com.example.gambungstore.models.Profile;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Services {

    @FormUrlEncoded
    @POST("users")
    Call<ResponseBody> registerProses(
            @Field("username") String username,
            @Field("email") String email,
            @Field("nama") String nama,
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("city") int city,
            @Field("password_confirmation") String rePassword,
            @Field("role") String role
    );

    @FormUrlEncoded
    @POST("login")
    Call<Login> loginProcess(
            @Field("email") String email,
            @Field("password") String password,
            @Field("role") String role
    );

    @GET("profile")
    Call<Profile> getProfile(
            @Header("Authorization") String token
    );



}
