package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FormPenarikanBuyer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_penarikan_buyer);
    }

    public void Konfirmasiformbtn(View view) {
        Intent intent = new Intent(FormPenarikanBuyer.this, CheckoutPenarikanBuyerProses.class);
        startActivity(intent);
    }
}
