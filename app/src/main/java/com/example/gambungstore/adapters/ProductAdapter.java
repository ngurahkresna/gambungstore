package com.example.gambungstore.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gambungstore.R;
import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.product.DataProduct;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private static final String TAG = "ProductAdapter";
    
    private List<DataProduct> listProduct;
    private Context context;

    public ProductAdapter(List<DataProduct> listProduct, Context context) {
        this.listProduct = listProduct;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_product, parent, false);
        ProductAdapter.MyViewHolder mViewHolder = new ProductAdapter.MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataProduct productPosition = listProduct.get(position);
        holder.mTitle.setText(productPosition.getName());
        holder.mPrice.setText("Rp "+productPosition.getPrice());
        if (productPosition.getImages().isEmpty() == false){
            Glide.with(context)
                    .load(Client.IMAGE_URL+productPosition.getImages().get(0).getImage_name())
                    .into(holder.mImageView);
        }
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTitle;
        TextView mPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.productBackground);
            mTitle = itemView.findViewById(R.id.productTitle);
            mPrice = itemView.findViewById(R.id.productPrice);
        }
    }
}
