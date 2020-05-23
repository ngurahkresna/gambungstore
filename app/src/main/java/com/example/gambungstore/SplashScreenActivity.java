package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.product.DataProduct;
import com.example.gambungstore.services.Services;

import static com.example.gambungstore.client.Client.PRODUCT_URL;

public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = "detailProduct";
    private Services service;
    String mId;
    String mCode;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        service = Client.getClient(Client.BASE_URL).create(Services.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                intent = getIntent();
                Uri uri = intent.getData();
                if (uri != null) {
                    String uri_string = "URI: " + uri.toString();
                    Log.d("getLink", uri_string);
                    mCode = uri_string.replaceAll("URI: " + PRODUCT_URL, "");
                    getProductIdByCode(mCode);
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, homeActivity.class));
                }
            }
        }, 1600);

    }

    private void getProductIdByCode(String code) {
        Log.d("iniKode", code);
        Call<DataProduct> callProductByCode = service.getProductByCode(code);
        callProductByCode.enqueue(new Callback<DataProduct>() {
            @Override
            public void onResponse(Call<DataProduct> call, Response<DataProduct> response) {
                DataProduct dataProduct = response.body();

                mId = String.valueOf(dataProduct.getId());

                intent = new Intent(SplashScreenActivity.this, detailProduct.class);
                intent.putExtra("product_id", mId);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<DataProduct> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }
}
