package com.gambungstore.buyer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gambungstore.buyer.adapters.ProductAdapter;
import com.gambungstore.buyer.client.Client;
import com.gambungstore.buyer.models.product.DataProduct;
import com.gambungstore.buyer.models.wishlist.Wishlist;
import com.gambungstore.buyer.services.Services;
import com.gambungstore.buyer.sharedpreference.SharedPreference;

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
                finish();
                Intent intent = new Intent(wishlistActivity.this,homeActivity.class);
                startActivity(intent);
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
                Toast.makeText(wishlistActivity.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
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
