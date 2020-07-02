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
import com.example.gambungstore.models.BeritaDonasi;

import java.util.List;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.ViewHolder> {

    Context context;
    List<BeritaDonasi> berita;
    public BeritaAdapter(Context context, List<BeritaDonasi> berita)
    {
        this.context = context;
        this.berita = berita;
    }

    @NonNull
    @Override
    public BeritaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View donasiItem = inflater.inflate(R.layout.donasi_item, parent, false);
        return new ViewHolder(donasiItem);
    }

    @Override
    public void onBindViewHolder(@NonNull BeritaAdapter.ViewHolder holder, int position) {
        BeritaDonasi beritaDonasi = berita.get(position);
        ImageView img = holder.img;
        TextView judul = holder.judul;
        TextView isi = holder.isi;

        judul.setText(beritaDonasi.getJudul());
        isi.setText(beritaDonasi.getIsi());
        img.setImageResource(beritaDonasi.getFoto());
    }

    @Override
    public int getItemCount() {
        return berita.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public CardView card;
        public ImageView img;
        public TextView judul;
        public TextView isi;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            card = (CardView) itemView.findViewById(R.id.card_view_donasi);
            img = (ImageView) itemView.findViewById(R.id.gambar_don);
            judul = (TextView) itemView.findViewById(R.id.judulDonasi);
            isi = (TextView) itemView.findViewById(R.id.isiDonasi);

            
        }
    }
}
