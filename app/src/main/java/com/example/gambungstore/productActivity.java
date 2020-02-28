package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.gambungstore.adapters.ProductAdapter;
import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.product.DataProduct;
import com.example.gambungstore.models.product.Product;
import com.example.gambungstore.services.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class productActivity extends AppCompatActivity {

    private static final String TAG = "productActivity";

    private ImageView buttonBack;
    private RecyclerView product;
    private GridLayoutManager setLayoutManagerProduct;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        product = findViewById(R.id.product);

        buttonBack = findViewById(R.id.backButton);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(productActivity.this, homeFragment.class));
            }
        });

        getProduct();
    }

    public void onViewProduct(List<DataProduct> dataProducts) {
        product.setHasFixedSize(true);

        // use a linear layout manager
        setLayoutManagerProduct = new GridLayoutManager(productActivity.this , 3);
        product.setLayoutManager(setLayoutManagerProduct);

        // specify an adapter (see also next example)
        productAdapter = new ProductAdapter(dataProducts,this);
        product.setAdapter(productAdapter);
    }

    public void getProduct(){
        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<Product> callProduct = service.getProduct();
        callProduct.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                List<DataProduct> dataProduct = response.body().getProducts();
                onViewProduct(dataProduct);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });
    }
}
