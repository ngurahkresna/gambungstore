package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.example.gambungstore.adapters.BeritaAdapter;
import com.example.gambungstore.models.BeritaDonasi;
import java.util.ArrayList;

public class MainDonasi extends AppCompatActivity {
    ArrayList<BeritaDonasi> listBerita;
    RecyclerView beritaDonasi;
    BeritaAdapter beritaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_donasi);

        beritaDonasi = findViewById(R.id.recyDonasi);
        listBerita = new ArrayList<>();

        for(int i = 0; i < 6; i++){
            listBerita.add(new BeritaDonasi(R.drawable.placeholder_img, "Judul","abc"+i));
        }

        beritaAdapter = new BeritaAdapter(this, listBerita);

        beritaDonasi.setLayoutManager(new LinearLayoutManager(this));
        beritaDonasi.setAdapter(beritaAdapter);
    }

    public void beriDonasi(View view) {
        Intent intent = new Intent(this, MasukNominal.class);
        startActivity(intent);
    }

    public void detailDonasi(View view) {
        Intent intent = new Intent(this, DetailDonasi.class);
        startActivity(intent);
    }
}
