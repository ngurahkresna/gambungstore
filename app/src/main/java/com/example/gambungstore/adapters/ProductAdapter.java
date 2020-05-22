package com.example.gambungstore.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.gambungstore.R;
import com.example.gambungstore.client.Client;
import com.example.gambungstore.detailProduct;
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
        holder.mPrice.setText("Rp " + productPosition.getPrice());
        holder.mId = String.valueOf(productPosition.getId());

        RequestOptions myOptions = new RequestOptions()
                .fitCenter() // or centerCrop
                .override(300, 300);

        if (productPosition.getImages() != null) {
            if (!productPosition.getImages().isEmpty()) {
                Glide.with(context)
                        .load(Client.IMAGE_URL + productPosition.getImages().get(0).getImage_name())
                        .thumbnail(/*sizeMultiplier=*/ 0.25f)
                        .into(holder.mImageView);
            }
        }

        if (productPosition.getStock() < 1) {
            holder.mImageView.setAlpha((float) 0.5);
            holder.mOutOfStockText.setVisibility(View.VISIBLE);
        }

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, detailProduct.class);
                intent.putExtra("product_id", holder.mId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTitle;
        TextView mPrice;
        String mId;
        TextView mOutOfStockText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.productBackground);
            mTitle = itemView.findViewById(R.id.productTitle);
            mPrice = itemView.findViewById(R.id.productPrice);
            mOutOfStockText = itemView.findViewById(R.id.outOfStockText);
        }
    }
}
