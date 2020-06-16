package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class NotifikasiJumlahPenarikanGagal extends AppCompatActivity {

    private ImageView mBackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi_jumlah_penarikan_gagal);

        mBackArrow = findViewById(R.id.backArrow);
        mBackArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(NotifikasiJumlahPenarikanGagal.this,FormPenarikanBuyer.class);
                startActivity(intent);
            }
        });
    }

    public void btnKembali(View view) {
        Intent intent = new Intent(NotifikasiJumlahPenarikanGagal.this,FormPenarikanBuyer.class);
        startActivity(intent);
    }
}
