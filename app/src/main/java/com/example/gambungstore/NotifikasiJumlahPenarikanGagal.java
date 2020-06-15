package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NotifikasiJumlahPenarikanGagal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi_jumlah_penarikan_gagal);
    }

    public void btnKembali(View view) {
        Intent intent = new Intent(NotifikasiJumlahPenarikanGagal.this,FormPenarikanBuyer.class);
        startActivity(intent);
    }
}
