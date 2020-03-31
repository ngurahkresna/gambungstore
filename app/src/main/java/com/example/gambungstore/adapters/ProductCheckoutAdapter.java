package com.example.gambungstore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gambungstore.R;
import com.example.gambungstore.models.cart.DataCart;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductCheckoutAdapter extends RecyclerView.Adapter<ProductCheckoutAdapter.MyViewHolder> {

    private List<DataCart> dataCart;
    public Context context;

    public ProductCheckoutAdapter() {

    }

    @NonNull
    @Override
    public ProductCheckoutAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_checkout, parent, false);
        ProductCheckoutAdapter.MyViewHolder mViewHolder = new ProductCheckoutAdapter.MyViewHolder(mView);
        return mViewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull ProductCheckoutAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
