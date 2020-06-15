package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CheckoutPenarikanBuyerProses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_penarikan_buyer_proses);
    }

    public void btnHome(View view) {
        Intent intent = new Intent(CheckoutPenarikanBuyerProses.this,homeActivity.class);
        startActivity(intent);
    }

    public void btnKonfirmasipenarikan(View view) {
        Intent intent = new Intent(CheckoutPenarikanBuyerProses.this,NotifikasiBerhasil.class);
        startActivity(intent);
    }
}
