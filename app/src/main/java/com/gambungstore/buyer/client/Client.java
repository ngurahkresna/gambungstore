package com.gambungstore.buyer.client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    public static final String BASE_URL = "https://gambungstore.id/api/";
    public static final String IMAGE_URL = "https://gambungstore.id/assets/img/products";
    public static final String PRODUCT_URL = "https://gambungstore.id/detail-produk/";

    private static Retrofit retrofit;
    public static Retrofit getClient(String url) {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static Retrofit getRajaongkir() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.rajaongkir.com/starter/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

}
