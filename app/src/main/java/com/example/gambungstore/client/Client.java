package com.example.gambungstore.client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    public static final String BASE_URL = "http://gambungstore.id/api/";
    public static final String IMAGE_URL = "http://gambungstore.id/assets/img/products";

    private static Retrofit retrofit;
    public static Retrofit getClient(String url) {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

}
