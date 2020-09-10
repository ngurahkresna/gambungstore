package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.gambungstore.adapters.LaporanAdapter;
import com.example.gambungstore.models.LaporanDonasi;
import com.example.gambungstore.sharedpreference.SharedPreference;

import java.util.ArrayList;

public class DetailDonasi extends AppCompatActivity {
    ArrayList<LaporanDonasi> listLaporan;
    RecyclerView recyclerView;
    LaporanAdapter laporanAdapter;
    int kasdonasi = 2020000000;
    int penarikandonasi = 20000000;
    int totalkas = 2000000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_donasi);
        listLaporan = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            listLaporan.add(new LaporanDonasi("Pembelian traktor", R.drawable.placeholder_logo, "Pak Dede Yusuf",
                    "Membutuhkan dana untuk meningkatkan produksi dengan menambah mesin produksi",kasdonasi, penarikandonasi++,totalkas));
            kasdonasi = totalkas;
            totalkas = kasdonasi - penarikandonasi;
        }

        recyclerView = findViewById(R.id.laporanDon);
        laporanAdapter = new LaporanAdapter(this, listLaporan);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(laporanAdapter);
    }

    public void beriDonasi(View view) {
        if(!SharedPreference.getRegisteredToken(this).matches(""))
        {
            Intent intent = new Intent(this, MasukNominal.class);
            startActivity(intent);
        } else
        {
            Toast.makeText(DetailDonasi.this,"Anda perlu login terlebih dahulu",Toast.LENGTH_SHORT);
        }
    }
}
