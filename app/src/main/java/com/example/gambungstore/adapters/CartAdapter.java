package com.example.gambungstore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gambungstore.R;
import com.example.gambungstore.models.cart.DataCart;

import org.w3c.dom.Text;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>  {

    private List<DataCart> dataCart;
    public Context context;

    public CartAdapter(List<DataCart> dataCart, Context context) {
        this.dataCart = dataCart;
        this.context = context;
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cart, parent, false);
        CartAdapter.MyViewHolder mViewHolder = new CartAdapter.MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        DataCart cartPosition = dataCart.get(position);
        holder.mNameProduct.setText(cartPosition.getProduct().getName().toString());
        holder.mPriceProduct.setText("Harga : Rp "+cartPosition.getPrice()+",-");
        holder.mQuantityProduct.setText("Kuantitas : "+cartPosition.getQuantity()+" pcs");
    }

    @Override
    public int getItemCount() {
        return dataCart.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mNameProduct,mQuantityProduct,mPriceProduct;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameProduct = itemView.findViewById(R.id.cartTitle);
            mQuantityProduct = itemView.findViewById(R.id.cartQuantity);
            mPriceProduct = itemView.findViewById(R.id.cartPrice);

        }
    }
}
