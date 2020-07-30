package com.example.gambungstore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gambungstore.models.LaporanDonasi;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BottomSheetFragmentLaporan extends BottomSheetDialogFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View newView = inflater.inflate(R.layout.fragment_bottom_sheet_laporan, container, false);
        Bundle bundle = getArguments();
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("in","ID"));
        String kasS = format.format(bundle.getInt("nominalkas"));
        String totalkasS = format.format(bundle.getInt("totalkas"));
        String penarikanS = format.format(bundle.getInt("penarikan"));

        TextView judul = newView.findViewById(R.id.judul_laporan);
        ImageView profil_penerima = newView.findViewById(R.id.fp_penerima);
        TextView user = newView.findViewById(R.id.user_penerima);
        TextView deskripsi = newView.findViewById(R.id.isi_donasi);
        TextView kas = newView.findViewById(R.id.nominal_kas);
        TextView penarikan = newView.findViewById(R.id.nominal_penarikan);
        TextView totalkas = newView.findViewById(R.id.nominal_total);

        judul.setText(bundle.getString("judul"));
        profil_penerima.setImageResource(bundle.getInt("img"));
        user.setText(bundle.getString("username"));
        deskripsi.setText(bundle.getString("deskripsi"));
        kas.setText(kasS);
        penarikan.setText(penarikanS);
        totalkas.setText(totalkasS);

        return newView;
    }
}
