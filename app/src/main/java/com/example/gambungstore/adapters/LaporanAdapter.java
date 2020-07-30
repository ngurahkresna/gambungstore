package com.example.gambungstore.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gambungstore.BottomSheetFragmentLaporan;
import com.example.gambungstore.R;
import com.example.gambungstore.models.LaporanDonasi;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import java.util.List;

public class LaporanAdapter extends RecyclerView.Adapter<LaporanAdapter.ViewHolder> {
    Context context;
    List<LaporanDonasi> laporan;

    public LaporanAdapter(Context context, List<LaporanDonasi> laporan) {
        this.context = context;
        this.laporan = laporan;
    }

    @NonNull
    @Override
    public LaporanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View laporanItem = inflater.inflate(R.layout.laporan_item, parent, false);
        return new ViewHolder(laporanItem);
    }

    @Override
    public void onBindViewHolder(@NonNull LaporanAdapter.ViewHolder holder, int position) {
        LaporanDonasi laporanDonasi = laporan.get(position);
        ImageView profpic = holder.profpic;
        TextView penerima = holder.penerima;
        TextView deskripsi = holder.deskripsi;
        TextView jumlah =  holder.jumlah;
        Bundle bundle = new Bundle();
        FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();

        bundle.putString("judul",laporanDonasi.getJudulLaporan());
        bundle.putInt("img",laporanDonasi.getUserImage());
        bundle.putString("deskripsi",laporanDonasi.getDeskripsiDonasi());
        bundle.putString("username",laporanDonasi.getUsernamePenerima());
        bundle.putInt("nominalkas",laporanDonasi.getKas_donasi());
        bundle.putInt("totalkas",laporanDonasi.getTotal_kas());
        bundle.putInt("penarikan",laporanDonasi.getPenarikan_donasi());

        profpic.setImageResource(laporanDonasi.getUserImage());
        penerima.setText(laporanDonasi.getUsernamePenerima());
        deskripsi.setText(laporanDonasi.getJudulLaporan());
        jumlah.setText(laporanDonasi.getPenarikan_donasi()+"");
        holder.newDialog.setArguments(bundle);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.newDialog.show(manager, holder.newDialog.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        return laporan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        BottomSheetFragmentLaporan newDialog;
        public CardView card;
        public ImageView profpic;
        public TextView penerima;
        public TextView deskripsi;
        public TextView jumlah;

        public ViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card_view_laporan);
            profpic = (ImageView) itemView.findViewById(R.id.profPic);
            penerima = (TextView) itemView.findViewById(R.id.tujuan_donasi);
            deskripsi = (TextView) itemView.findViewById(R.id.deskripsi_donasi);
            jumlah = (TextView) itemView.findViewById(R.id.jumlah_donasi);
            newDialog = new BottomSheetFragmentLaporan();
        }
    }
}
