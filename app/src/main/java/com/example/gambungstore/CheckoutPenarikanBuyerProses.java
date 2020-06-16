package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CheckoutPenarikanBuyerProses extends AppCompatActivity {

    private ImageView mBackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_penarikan_buyer_proses);

        mBackArrow = findViewById(R.id.backArrow);
        mBackArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(CheckoutPenarikanBuyerProses.this,FormPenarikanBuyer.class);
                startActivity(intent);
            }
        });
    }

    public void btnHome(View view) {
        finish();
        Intent intent = new Intent(CheckoutPenarikanBuyerProses.this,homeActivity.class);
        startActivity(intent);
    }


    public void btnKonfirmasipenarikan(View view) {
        Intent intent = new Intent(CheckoutPenarikanBuyerProses.this,NotifikasiBerhasil.class);
        startActivity(intent);
    }
}
