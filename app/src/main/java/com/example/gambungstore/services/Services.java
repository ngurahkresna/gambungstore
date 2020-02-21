package com.example.gambungstore.services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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



}
