package com.example.gambungstore.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gambungstore.R;
import com.example.gambungstore.detailPromo;
import com.example.gambungstore.models.promo.DataPromo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PromoAdapterMatchParent extends RecyclerView.Adapter<PromoAdapterMatchParent.MyViewHolder> {

    private List<DataPromo> dataPromo;
    private Context context;

    public PromoAdapterMatchParent(List<DataPromo> dataPromo, Context context) {
        this.dataPromo = dataPromo;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_promo_matchparent, parent, false);
        PromoAdapterMatchParent.MyViewHolder mViewHolder = new PromoAdapterMatchParent.MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final DataPromo positionDataPromo = dataPromo.get(position);
        holder.mPromoTitle.setText(positionDataPromo.getCode().toString());
        holder.mPromoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, detailPromo.class);
                intent.putExtra("promo", positionDataPromo);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.dataPromo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mPromoTitle;
        ImageView mPromoCard;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mPromoTitle = itemView.findViewById(R.id.promoTitle);
            this.mPromoCard = itemView.findViewById(R.id.promoCard);
        }
    }
}
