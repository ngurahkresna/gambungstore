package com.example.gambungstore.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gambungstore.R;
import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.cart.DataCart;
import com.example.gambungstore.models.product.DataProduct;
import com.example.gambungstore.sharedpreference.SharedPreference;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class ProductCheckoutAdapter extends RecyclerView.Adapter<ProductCheckoutAdapter.MyViewHolder> {
    private static final String TAG = "ProductCheckoutAdapter";
    
    private List<DataProduct> dataProducts;
    public Context context;

    public ProductCheckoutAdapter(List<DataProduct> dataProducts, Context context) {
        this.dataProducts = dataProducts;
        this.context = context;
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
        final DataProduct productPosition = dataProducts.get(position);
        Log.d(TAG, "onBindViewHolder: "+productPosition.getCarts().size());
        if (productPosition.getCarts().size() != 0){
            for (int i = 0; i < productPosition.getCarts().size(); i++){
                if (productPosition.getCarts().get(i).getUsername().equals(SharedPreference.getRegisteredUsername(context))){
                    Log.d(TAG, "onBindViewHolder: "+productPosition.getCarts().get(i).getUsername());
                    holder.mProductName.setText(productPosition.getName());
                    holder.mProductPrice.setText("Rp "+Integer.toString(productPosition.getPrice())+",-");
                    holder.mStoreName.setText("");
                    Glide.with(context).load(Client.IMAGE_URL+productPosition.getImages().get(0).getImage_name()).into(holder.mProductImage);
                }
            }
        }else{
            holder.mProduct.setVisibility(View.GONE);
            holder.mProductComment.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return dataProducts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mProductImage;
        TextView mProductName;
        TextView mProductPrice;
        TextView mStoreName;
        LinearLayout mProduct,mProductComment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mProductImage = itemView.findViewById(R.id.productImage);
            mProductName = itemView.findViewById(R.id.ProductName);
            mProductPrice = itemView.findViewById(R.id.ProductPrice);
            mStoreName = itemView.findViewById(R.id.storeName);
            mProduct = itemView.findViewById(R.id.product);
            mProductComment = itemView.findViewById(R.id.product_comment);

        }
    }
}
