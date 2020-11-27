package com.gambungstore.buyer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class jicashCheckoutDone extends AppCompatActivity {
    Button backButton, detailButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jicash_checkout_done);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(jicashCheckoutDone.this, homeActivity.class);
                startActivity(intent);
            }
        });

        detailButton = findViewById(R.id.detailTransaksi);

        // Masih ngebug tampilannya. Ketimpa gitu jadinya
        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new transaction();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.jicashCheckoutDoneLayout,fragment).addToBackStack(transaction.class.getSimpleName()).commit();
            }
        });
    }
}
