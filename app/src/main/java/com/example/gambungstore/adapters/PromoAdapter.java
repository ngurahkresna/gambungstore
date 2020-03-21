package com.example.gambungstore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gambungstore.R;
import com.example.gambungstore.models.promo.DataPromo;
import com.example.gambungstore.models.promo.Promo;

import java.util.List;

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.MyViewHolder> {

    private List<DataPromo> dataPromo;
    private Context context;

    public PromoAdapter(List<DataPromo> dataPromo, Context context) {
        this.dataPromo = dataPromo;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_promo, parent, false);
        PromoAdapter.MyViewHolder mViewHolder = new PromoAdapter.MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final DataPromo positionDataPromo = dataPromo.get(position);
        holder.mPromoTitle.setText(positionDataPromo.getCode().toString());
    }

    @Override
    public int getItemCount() {
        return this.dataPromo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mPromoTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mPromoTitle = itemView.findViewById(R.id.promoTitle);
        }
    }
}
