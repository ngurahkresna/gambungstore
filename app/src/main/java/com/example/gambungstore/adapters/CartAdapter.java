package com.example.gambungstore.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gambungstore.R;
import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.cart.DataCart;
import com.example.gambungstore.services.Services;

import java.net.ResponseCache;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>  {
    private static final String TAG = "CartAdapter";

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
        Log.d(TAG, "onBindViewHolder: "+cartPosition.getId());
        holder.mNameProduct.setText(cartPosition.getProduct().getName().toString());
        holder.mPriceProduct.setText("Harga : Rp "+cartPosition.getPrice()+",-");
        holder.mQuantityProduct.setText("Kuantitas : "+cartPosition.getQuantity()+" pcs");
        if (cartPosition.getProduct().getImages() != null){
            if (!cartPosition.getProduct().getImages().isEmpty()) {
                Glide.with(context)
                        .load(Client.IMAGE_URL + cartPosition.getProduct().getImages())
                        .into(holder.mImageProduct);
            }
        }
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setMessage("apakah anda yakin ?");
                alert.setTitle("Warning");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCart(cartPosition.getId(), position);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return dataCart.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mNameProduct, mQuantityProduct, mPriceProduct;
        ImageView mImageProduct;
        Button mDeleteButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameProduct = itemView.findViewById(R.id.cartTitle);
            mQuantityProduct = itemView.findViewById(R.id.cartQuantity);
            mPriceProduct = itemView.findViewById(R.id.cartPrice);
            mImageProduct = itemView.findViewById(R.id.cartBackground);
            mDeleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    private void deleteCart(int id, int position){
        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<ResponseBody> callCart = service.deleteCart(
                id
        );
        callCart.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: "+response.body());
                Log.d(TAG, "onResponse: "+response.raw());
                dataCart.remove(position);
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });
    }
}
