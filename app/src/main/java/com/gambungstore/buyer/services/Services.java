package com.gambungstore.buyer.services;

import com.gambungstore.buyer.models.BankAccount;
import com.gambungstore.buyer.models.Login;
import com.gambungstore.buyer.models.Profile;
import com.gambungstore.buyer.models.RajaOngkir;
import com.gambungstore.buyer.models.Store;
import com.gambungstore.buyer.models.cart.Cart;
import com.gambungstore.buyer.models.category.Category;
import com.gambungstore.buyer.models.checkout.Checkout;
import com.gambungstore.buyer.models.category.DataCategory;
import com.gambungstore.buyer.models.jicash.HistoryJicash;
import com.gambungstore.buyer.models.jicash.Jicash;
import com.gambungstore.buyer.models.product.DataProduct;
import com.gambungstore.buyer.models.product.Product;
import com.gambungstore.buyer.models.promo.DataPromo;
import com.gambungstore.buyer.models.promo.Promo;
import com.gambungstore.buyer.models.transaction.DetailTransaction;
import com.gambungstore.buyer.models.transaction.Transaction;
import com.gambungstore.buyer.models.voucher.Voucher;
import com.gambungstore.buyer.models.wishlist.Wishlist;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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
    @POST("check-password")
    Call<ResponseBody> checkOldPass(
            @Header("Authorization") String token,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("update-password")
    Call<ResponseBody> updatePass(
            @Header("Authorization") String token,
            @Field("password") String password,
            @Field("password_confirmation") String confirmPassword
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

    @FormUrlEncoded
    @POST("category/search")
    Call<List<DataCategory>> searchCategory(
            @Field("value") String key
    );

    @GET("product")
    Call<Product> getProduct();

    @GET("product/{id}")
    Call<DataProduct> getProductById(
            @Path("id") String id
    );

    @GET("product")
    Call<DataProduct> getProductByCode(
            @Query("code") String code
    );

    @FormUrlEncoded
    @POST("product/search")
    Call<List<DataProduct>> searchProduct(
            @Field("value") String key
    );

    @FormUrlEncoded
    @POST("product/search")
    Call<List<DataProduct>> searchProductbyCategory(
            @Field("value[]") ArrayList<String> key
    );

    @GET("cart")
    Call<Cart> getCart(
            @Query("username") String username
    );

    @FormUrlEncoded
    @POST("cart")
    Call<ResponseBody> storeCart(
            @Field("product_code") String product_code,
            @Field("quantity") int quantity,
            @Field("username") String username
    );

    @GET("wishlist")
    Call<Wishlist> getWishlist();


    @GET("voucher")
    Call<Promo> getPromo();

    @FormUrlEncoded
    @POST("voucher/search")
    Call<List<DataPromo>> searchPromo(
            @Field("value") String value
    );

    @DELETE("cart/{id}")
    Call<ResponseBody> deleteCart(
            @Path("id") int id
    );

    @GET("wishlist")
    Call<Wishlist> getWishlistByUserId(
            @Query("user_id") int user_id
    );

    @DELETE("wishlist/{id}")
    Call<ResponseBody> deleteWishlistById(
            @Path("id") int id
    );

    @FormUrlEncoded
    @POST("cart/checkout")
    Call<ResponseBody> checkout(
            @Field("cart_id[]") ArrayList<Integer> cart_id,
            @Field("quantity[]") ArrayList<Integer> quantity,
            @Field("username") String username
    );

    @GET("checkout")
    Call<Checkout> getCheckout(
            @Query("username") String username
    );

    @GET("voucher/{code}")
    Call<Voucher> getVoucher(
            @Path("code") String code
    );

    @FormUrlEncoded
    @POST("checkout")
    Call<DetailTransaction> processCheckout(
            @Field("username") String username,
            @Field("address") String address,
            @Field("phone") String phone,
            @Field("store_id[]") ArrayList<String> store_id,
            @Field("expedition[]") ArrayList<String> expedition,
            @Field("total_shipping_charges") int total_shipping_charges,
            @Field("total_product_amount") int total_product_amount,
            @Field("total_discount_amount") int total_discount_amount,
            @Field("grand_total") int grand_total,
            @Field("payment_method_id") int payment_method_id,
            @Field("product_code[]") ArrayList<String> product_code,
            @Field("message[]") ArrayList<String> message,
            @Field("voucher") String voucher
    );

    @Multipart
    @POST("upload-proof")
    Call<ResponseBody> uploadProof(
            @Part("transaction_code") RequestBody transaction_code,
            @Part MultipartBody.Part proof_image,
            @Part("username") RequestBody username
    );

    @FormUrlEncoded
    @POST("wishlist")
    Call<ResponseBody> storeWishlist(
            @Field("user_id") int user_id,
            @Field("product_code") String product_code
    );

    @Headers("key: af45d33fa137d07654770aea61ab3dc1")
    @GET("city")
    Call<RajaOngkir> getRajaongkir();

    @GET("users")
    Call<List<Profile>> getUsers();

    @GET("transaction")
    Call<Transaction> getTransactionByUsername(
            @Query("username") String username
    );

    @GET("store/{id}")
    Call<Store> getStoreById(
            @Path("id") int id
    );

    @GET("product")
    Call<Product> searchProductInStore(
            @Query("store_code") String store_code,
            @Query("product_name") String product_name
    );

    @FormUrlEncoded
    @POST("transaction/cancel")
    Call<ResponseBody> cancelTransaction(
            @Field("transaction_code") String transaction_code,
            @Field("username") String username,
            @Field("product_code") String product_code
    );

    @FormUrlEncoded
    @POST("transaction/accept")
    Call<ResponseBody> acceptTransaction(
            @Field("transaction_code") String transaction_code,
            @Field("username") String username,
            @Field("product_code") String product_code
    );

    @GET("jicash/history")
    Call<List<HistoryJicash>> getHistoryJicash(
            @Query("username") String username
    );

    @Multipart
    @POST("jicash/topup")
    Call<ResponseBody> uploadProofJicash(
            @Part("amount") RequestBody ammount,
            @Part MultipartBody.Part topup_proof,
            @Part("username") RequestBody username
    );

    @Multipart
    @POST("jicash/topup")
    Call<ResponseBody> updateProofJicash(
            @Part("jicash_id") RequestBody jicash_id,
            @Part MultipartBody.Part topup_proof,
            @Part("username") RequestBody username
    );

    @GET("jicash")
    Call<List<Jicash>> getJicash(
        @Query("username") String username
    );

    @GET("bank-account/active")
    Call<BankAccount> getBankAccountActive();
}
