package com.gambungstore.buyer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gambungstore.buyer.adapters.ProductAdapter;
import com.gambungstore.buyer.client.Client;
import com.gambungstore.buyer.models.product.DataProduct;
import com.gambungstore.buyer.models.product.Product;
import com.gambungstore.buyer.services.Services;

import java.util.ArrayList;
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
    private EditText searchHint;
    private LinearLayout btnSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        product = findViewById(R.id.product);
        buttonBack = findViewById(R.id.backButton);
        searchHint = findViewById(R.id.searchHint);

        btnSearch = findViewById(R.id.searchButton);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchProduct(searchHint.getText().toString());
            }
        });

        searchHint.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchProduct(searchHint.getText().toString());
                return true;
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //cek apakah dari search activity atau bukan
        if (getIntent().getStringExtra("status") != null){
            if (getIntent().getStringExtra("status").equals("search")){
                List<DataProduct> parseDataProduct = getIntent().getParcelableArrayListExtra("dataproduct");
                onViewProduct(parseDataProduct);
            }else if(getIntent().getStringExtra("status").equals("category")){
                searchProductbyCategory(getIntent().getStringExtra("key"));
            }
        }else{
            getProduct();
        }

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
                Toast.makeText(productActivity.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchProduct(String key){
        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<List<DataProduct>> callSearch = service.searchProduct(key);
        callSearch.enqueue(new Callback<List<DataProduct>>() {
            @Override
            public void onResponse(Call<List<DataProduct>> call, Response<List<DataProduct>> response) {
                List<DataProduct> dataProducts = response.body();
                if (dataProducts.isEmpty()){
                    Toast.makeText(productActivity.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    return;
                }
                onViewProduct(dataProducts);
            }

            @Override
            public void onFailure(Call<List<DataProduct>> call, Throwable t) {
                Toast.makeText(productActivity.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchProductbyCategory(String key){

        ArrayList<String> keyArray = new ArrayList<>();
        keyArray.add(key);

        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<List<DataProduct>> callSearch = service.searchProductbyCategory(keyArray);
        callSearch.enqueue(new Callback<List<DataProduct>>() {
            @Override
            public void onResponse(Call<List<DataProduct>> call, Response<List<DataProduct>> response) {
                List<DataProduct> dataProducts = response.body();
                if (dataProducts.isEmpty()){
                    Toast.makeText(productActivity.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d(TAG, "onResponse: "+response.body().size());
                onViewProduct(dataProducts);
            }

            @Override
            public void onFailure(Call<List<DataProduct>> call, Throwable t) {
                Toast.makeText(productActivity.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
