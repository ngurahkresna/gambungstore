package com.example.gambungstore.services;

import com.example.gambungstore.models.Login;
import com.example.gambungstore.models.Profile;
import com.example.gambungstore.models.RajaOngkir;
import com.example.gambungstore.models.cart.Cart;
import com.example.gambungstore.models.category.Category;
import com.example.gambungstore.models.product.DataProduct;
import com.example.gambungstore.models.product.Product;
import com.example.gambungstore.models.wishlist.Wishlist;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Services {

    @FormUrlEncoded
    @POST("users")
    Call<ResponseBody> registerProses(
            @Field("username") String username,
            @Field("email") String email,
            @Field("name") String name,
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("address") String address,
            @Field("city") String city,
            @Field("password_confirmation") String rePassword,
            @Field("role") String role,
            @Field("birthday") String birthday
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

    @FormUrlEncoded
    @PUT("users/{id}")
    Call<ResponseBody> updateProfile(
            @Path("id") int id,
            @Field("name") String nama,
            @Field("phone") String phone,
            @Field("birthday") String birthday,
            @Field("address") String address,
            @Field("city") int city
    );

    @GET("category")
    Call<Category> getCategory();

    @GET("product")
    Call<Product> getProduct();

    @FormUrlEncoded
    @POST("product/search")
    Call<List<DataProduct>> searchProduct(
      @Field("value") String key
    );

    @GET("cart")
    Call<Cart> getCart(
      @Query("username") String username
    );

    @GET("wishlist")
    Call<Wishlist> getWishlist();

    @Headers("key: e2f076d77998bbb2921165ee490297a4")
    @GET("city")
    Call <RajaOngkir> getRajaongkir();

    @GET("users")
    Call<List<Profile>> getUsers();
}
