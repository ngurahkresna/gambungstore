package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gambungstore.adapters.PromoAdapter;
import com.example.gambungstore.client.Client;
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
    private PromoAdapter promoAdapter;

    private Services service;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);

        promo = findViewById(R.id.promo);

        buttonBack = findViewById(R.id.backButton);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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
        promoAdapter = new PromoAdapter(dataPromos, this);
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
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });
    }
}
