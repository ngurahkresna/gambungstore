package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.gambungstore.adapters.ProductAdapter;
import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.product.DataProduct;
import com.example.gambungstore.models.wishlist.DataWishlist;
import com.example.gambungstore.models.wishlist.Wishlist;
import com.example.gambungstore.services.Services;
import com.example.gambungstore.sharedpreference.SharedPreference;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class wishlistActivity extends AppCompatActivity {
    private static final String TAG = "wishlistActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getWishlist();
    }

    private void getWishlist(){
        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<Wishlist> callWishlist = service.getWishlist();
        callWishlist.enqueue(new Callback<Wishlist>() {
            @Override
            public void onResponse(Call<Wishlist> call, Response<Wishlist> response) {
                List<DataProduct> userWishlist = new ArrayList<>();
                for(int i = 0; i < response.body().getDataWishlist().size(); i++){
                    if (response.body().getDataWishlist().get(i).getId_users()
                            == SharedPreference.getRegisteredId(getApplicationContext())){
                        userWishlist.add(response.body().getDataWishlist().get(i).getProduct());
                    }
                }
                getRecyclerView(userWishlist);
            }

            @Override
            public void onFailure(Call<Wishlist> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });
    }

    private void getRecyclerView(List<DataProduct> listWishlist){
        RecyclerView wishlist = findViewById(R.id.wishlistRecyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        ProductAdapter productAdapter = new ProductAdapter(listWishlist, this);

        wishlist.setLayoutManager(layoutManager);
        wishlist.setAdapter(productAdapter);
    }
}
