package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class FormPenarikanBuyer extends AppCompatActivity {

    private ImageView mBackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_penarikan_buyer);

        mBackArrow = findViewById(R.id.backArrow);
        mBackArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(FormPenarikanBuyer.this,jiCashHomeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void Konfirmasiformbtn(View view) {
        Intent intent = new Intent(FormPenarikanBuyer.this, CheckoutPenarikanBuyerProses.class);
        startActivity(intent);
    }
}
