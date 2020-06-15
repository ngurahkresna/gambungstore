package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NotifikasiBerhasil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi_berhasil);
    }

    public void btnHome(View view) {
        Intent intent = new Intent(NotifikasiBerhasil.this,homeActivity.class);
        startActivity(intent);
    }
}
