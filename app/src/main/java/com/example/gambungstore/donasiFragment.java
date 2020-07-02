package com.example.gambungstore;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gambungstore.adapters.BeritaAdapter;
import com.example.gambungstore.models.BeritaDonasi;

import java.util.ArrayList;

public class donasiFragment extends Fragment {
    ArrayList<BeritaDonasi> listBerita;
    RecyclerView beritaDonasi;
    BeritaAdapter beritaAdapter;
    Button beridonasi, detaildonasi;

    public static donasiFragment newInstance() {
        return new donasiFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.donasi_fragment, container, false);
        beridonasi = (Button)view.findViewById(R.id.mainBeriDonasi);
        detaildonasi = (Button)view.findViewById(R.id.mainDetailDonasi);

        beridonasi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MasukNominal.class);
                startActivity(intent);
            }
        });
        detaildonasi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailDonasi.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        beritaDonasi = view.findViewById(R.id.recyDonasi);
        listBerita = new ArrayList<>();

        for(int i = 0; i < 6; i++){
            listBerita.add(new BeritaDonasi(R.drawable.placeholder_img, "Judul","abc"+i));
        }

        beritaAdapter = new BeritaAdapter(getContext(), listBerita);

        beritaDonasi.setLayoutManager(new LinearLayoutManager(getContext()));
        beritaDonasi.setAdapter(beritaAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onClick(View v) {

    }
}
