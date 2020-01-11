package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class productActivity extends AppCompatActivity {

    private ImageView buttonBack;
    private RecyclerView product;
    private GridLayoutManager setLayoutManagerProduct;
    private adapterProduct productAdapter;

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

        onViewProduct();
    }

    public void onViewProduct() {
        product.setHasFixedSize(true);

        // use a linear layout manager
        setLayoutManagerProduct = new GridLayoutManager(productActivity.this , 3);
        product.setLayoutManager(setLayoutManagerProduct);

        // specify an adapter (see also next example)
        productAdapter = new adapterProduct();
        product.setAdapter(productAdapter);
    }
}
