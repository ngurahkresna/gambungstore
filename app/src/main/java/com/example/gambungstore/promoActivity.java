package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gambungstore.adapters.PromoAdapter;
import com.example.gambungstore.adapters.PromoAdapterMatchParent;
import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.category.DataCategory;
import com.example.gambungstore.models.promo.DataPromo;
import com.example.gambungstore.models.promo.Promo;
import com.example.gambungstore.services.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class promoActivity extends AppCompatActivity {
    private static final String TAG = "promoActivity";
    
    private ImageView buttonBack;
    private RecyclerView promo;
    private LinearLayoutManager setLayoutManager;
    private PromoAdapterMatchParent promoAdapter;
    private LinearLayout btnSearch;

    private EditText searchHint;

    private Services service;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);

        promo = findViewById(R.id.promo);

        buttonBack = findViewById(R.id.backButton);
        btnSearch = findViewById(R.id.searchButton);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchPromo(searchHint.getText().toString());
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        searchHint = findViewById(R.id.searchHint);
        searchHint.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {;
                searchPromo(searchHint.getText().toString());
                return true;
            }
        });

        service = Client.getClient(Client.BASE_URL).create(Services.class);

        getPromo();
    }

    public void onViewPromo(List<DataPromo> dataPromos) {
        promo.setHasFixedSize(true);

        // use a linear layout manager
        setLayoutManager = new LinearLayoutManager(promoActivity.this);
        promo.setLayoutManager(setLayoutManager);

        // specify an adapter (see also next example)
        promoAdapter = new PromoAdapterMatchParent(dataPromos, this);
        promo.setAdapter(promoAdapter);
    }

    private void getPromo(){
        Call<Promo> callPromo = service.getPromo();
        callPromo.enqueue(new Callback<Promo>() {
            @Override
            public void onResponse(Call<Promo> call, Response<Promo> response) {
                List<DataPromo> dataPromos = response.body().getDataPromo();
                onViewPromo(dataPromos);
            }

            @Override
            public void onFailure(Call<Promo> call, Throwable t) {
                Toast.makeText(promoActivity.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchPromo(String key){
        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<List<DataPromo>> callSearch = service.searchPromo(key);
        callSearch.enqueue(new Callback<List<DataPromo>>() {
            @Override
            public void onResponse(Call<List<DataPromo>> call, Response<List<DataPromo>> response) {
                List<DataPromo> dataCategories = response.body();
                if (dataCategories.isEmpty()){
                    Toast.makeText(promoActivity.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    onViewPromo(dataCategories);
                    return;
                }

                onViewPromo(dataCategories);
            }

            @Override
            public void onFailure(Call<List<DataPromo>> call, Throwable t) {
                Toast.makeText(promoActivity.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
