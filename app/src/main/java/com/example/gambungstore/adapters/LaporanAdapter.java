package com.example.gambungstore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gambungstore.R;
import com.example.gambungstore.models.LaporanDonasi;

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

        profpic.setImageResource(laporanDonasi.getImg());
        penerima.setText(laporanDonasi.getTujuanDonasi());
        deskripsi.setText(laporanDonasi.getDeskripsiDonasi());
        jumlah.setText(laporanDonasi.getJumlahDonasi());
    }

    @Override
    public int getItemCount() {
        return laporan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
        }
    }
}
