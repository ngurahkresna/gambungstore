package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class promoActivity extends AppCompatActivity {


    private RecyclerView promo;
    private LinearLayoutManager setLayoutManager;
    private adapterPromo promoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);

        onViewPromo();
    }

    public void onViewPromo() {
        promo.setHasFixedSize(true);

        // use a linear layout manager
        setLayoutManager = new LinearLayoutManager(promoActivity.this);
        promo.setLayoutManager(setLayoutManager);

        // specify an adapter (see also next example)
        promoAdapter = new adapterPromo();
        promo.setAdapter(promoAdapter);
    }
}
