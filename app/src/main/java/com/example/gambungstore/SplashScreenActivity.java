package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                Intent intent = getIntent();
                Uri uri = intent.getData();
                if (uri != null) {
                    String uri_string = "URI: " + uri.toString();
                    Log.d("recive link", uri_string);
                    intent = new Intent(SplashScreenActivity.this, detailProduct.class);
                    String productId = uri_string.replaceAll("URI: https://dev.gambungstore.id/detail-produk/", "");
                    intent.putExtra("product_id", productId);
                    Log.d("cut", productId);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, homeActivity.class));
                }
            }
        }, 1600);


    }
}
