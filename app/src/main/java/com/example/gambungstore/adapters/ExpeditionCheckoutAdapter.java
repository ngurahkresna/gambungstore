package com.example.gambungstore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gambungstore.R;
import com.example.gambungstore.models.cart.DataCart;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ExpeditionCheckoutAdapter extends RecyclerView.Adapter<ExpeditionCheckoutAdapter.MyViewHolder> {

    private List<DataCart> dataCart;
    public Context context;

    public ExpeditionCheckoutAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ExpeditionCheckoutAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expedition_card_checkout, parent, false);
        ExpeditionCheckoutAdapter.MyViewHolder mViewHolder = new ExpeditionCheckoutAdapter.MyViewHolder(mView);
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ExpeditionCheckoutAdapter.MyViewHolder holder, int position) {
        productAdapterCheckout(holder);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RecyclerView product;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product = itemView.findViewById(R.id.product);
        }
    }

    public void productAdapterCheckout(@NonNull ExpeditionCheckoutAdapter.MyViewHolder holder) {
        ProductCheckoutAdapter productAdapter = new ProductCheckoutAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        holder.product.setLayoutManager(linearLayoutManager);
        holder.product.setAdapter(productAdapter);
    }
}
