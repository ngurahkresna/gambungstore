package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.gambungstore.adapters.CategoryAdapter;
import com.example.gambungstore.adapters.ProductAdapter;
import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.category.DataCategory;
import com.example.gambungstore.models.product.DataProduct;
import com.example.gambungstore.models.product.Product;
import com.example.gambungstore.services.Services;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.List;

public class detailProduct extends AppCompatActivity {
    private static final String TAG = "detailProduct";

    RecyclerView suggestProduct;
    private Services service;
    CarouselView carouselView;

    int[] sampleImages = {R.drawable.auth_option_pitcure, R.drawable.facebook_logo, R.drawable.search};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        suggestProduct = findViewById(R.id.suggestProduct);

        service = Client.getClient(Client.BASE_URL).create(Services.class);
        getProduct();

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);
    }

    public void setSuggestProduct(List<DataProduct> dataProducts) {

        // use a linear layout manager
        LinearLayoutManager layout = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        suggestProduct.setLayoutManager(layout);

        // specify an adapter (see also next example)
        ProductAdapter productAdapter = new ProductAdapter(dataProducts, detailProduct.this);
        suggestProduct.setAdapter(productAdapter);

    }


    private void getProduct(){
        Call<Product> callProduct = service.getProduct();
        callProduct.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                List<DataProduct> dataProducts = response.body().getProducts();
                setSuggestProduct(dataProducts);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };
}
