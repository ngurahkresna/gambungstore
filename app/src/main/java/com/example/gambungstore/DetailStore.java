package com.example.gambungstore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gambungstore.adapters.StorePagerAdapter;
import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.Store;
import com.example.gambungstore.models.product.DataProduct;
import com.example.gambungstore.models.product.Product;
import com.example.gambungstore.progressbar.ProgressBarGambung;
import com.example.gambungstore.services.Services;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailStore extends AppCompatActivity {
    private String TAG = "DetailStoreActivity";

    private TabLayout tabLayout;

    Store store = new Store();
    int storeId;
    String storeCode;

    TextView storeName;
    TextView storeLocation;
    private ImageView buttonBack;
    private EditText searchHint;
    private LinearLayout btnSearch;

    ViewPager viewPager;
    StorePagerAdapter adapter;

    private ProgressBarGambung progressbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_store);

        progressbar = new ProgressBarGambung(this);

        storeName = findViewById(R.id.storeName);
        storeLocation = findViewById(R.id.storeLocation);
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Deskripsi Toko"));
        tabLayout.addTab(tabLayout.newTab().setText("Produk"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        storeId = getIntent().getIntExtra("store_id", 0);
        storeCode = getIntent().getStringExtra("store_code");

        btnSearch = findViewById(R.id.searchButton);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchProduct(searchHint.getText().toString());
            }
        });


        searchHint = findViewById(R.id.searchHint);
        searchHint.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                ;
                searchProduct(searchHint.getText().toString());
                return true;
            }
        });

        buttonBack = findViewById(R.id.backButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getStoreData(storeId);
    }

    void getStoreData(int storeId) {
        progressbar.startProgressBarGambung();

        Services services = Client.getClient(Client.BASE_URL).create(Services.class);
        services.getStoreById(storeId).enqueue(new Callback<Store>() {
            @Override
            public void onResponse(@NonNull Call<Store> call, @NonNull Response<Store> response) {
                progressbar.endProgressBarGambung();

                store = response.body();

                storeName.setText(store.getName());
                storeLocation.setText(store.getCity());

                showPager(store, store.getProducts());
            }

            @Override
            public void onFailure(@NonNull Call<Store> call, @NonNull Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    void showPager(Store store, List<DataProduct> products) {
        adapter = new StorePagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), store, products);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    void searchProduct(String key) {
        progressbar.startProgressBarGambung();

        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<Product> callSearch = service.searchProductInStore(storeCode, key);
        callSearch.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                List<DataProduct> products = response.body().getProducts();
                if (products.isEmpty()) {
                    progressbar.endProgressBarGambung();
                    Toast.makeText(DetailStore.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressbar.endProgressBarGambung();

                showPager(store, products);
                viewPager.setCurrentItem(1);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }
}