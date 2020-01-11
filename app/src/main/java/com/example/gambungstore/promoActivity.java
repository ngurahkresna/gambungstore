package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class promoActivity extends AppCompatActivity {

    private ImageView buttonBack;
    private RecyclerView promo;
    private LinearLayoutManager setLayoutManager;
    private adapterPromo promoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);

        promo = findViewById(R.id.promo);

        buttonBack = findViewById(R.id.backButton);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(promoActivity.this, homeFragment.class));
            }
        });

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
