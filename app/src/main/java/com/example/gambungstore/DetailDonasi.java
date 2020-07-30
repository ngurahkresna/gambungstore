package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.gambungstore.adapters.LaporanAdapter;
import com.example.gambungstore.models.LaporanDonasi;

import java.util.ArrayList;

public class DetailDonasi extends AppCompatActivity {
    ArrayList<LaporanDonasi> listLaporan;
    RecyclerView recyclerView;
    LaporanAdapter laporanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_donasi);
        listLaporan = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            listLaporan.add(new LaporanDonasi("Pembelian traktor", R.drawable.placeholder_logo, "Pak Dede Yusuf",
                    "Membutuhkan dana untuk meningkatkan produksi dengan menambah mesin produksi",2020000000, 20000000,2000000000));
        }

        recyclerView = findViewById(R.id.laporanDon);
        laporanAdapter = new LaporanAdapter(this, listLaporan);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(laporanAdapter);
    }

    public void beriDonasi(View view) {
        Intent intent = new Intent(this, MasukNominal.class);
        startActivity(intent);
    }
}
