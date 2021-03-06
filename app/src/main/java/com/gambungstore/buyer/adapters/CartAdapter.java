package com.gambungstore.buyer.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gambungstore.buyer.R;
import com.gambungstore.buyer.cartFragment;
import com.gambungstore.buyer.client.Client;
import com.gambungstore.buyer.homeActivity;
import com.gambungstore.buyer.models.cart.DataCart;
import com.gambungstore.buyer.progressbar.ProgressBarGambung;
import com.gambungstore.buyer.services.Services;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private static final String TAG = "CartAdapter";

    private List<DataCart> dataCart;
    public Context context;
    private ProgressBarGambung progressbar;
    private ArrayList<Integer> cartIds;
    private ArrayList<Integer> cartQuantities;
    private cartFragment fragment;
    private boolean isEmptyProductStock = false;

    public CartAdapter(List<DataCart> dataCart, Context context, cartFragment fragment) {
        this.dataCart = dataCart;
        this.context = context;
        progressbar = new ProgressBarGambung((homeActivity) context);
        this.fragment = fragment;
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

        int productStock = cartPosition.getProduct().getStock();
        holder.mNameProduct.setText(cartPosition.getProduct().getName());
        holder.mPriceProduct.setText("Harga : Rp " + cartPosition.getProduct().getPrice() + ",-");
        holder.mProductStock.setText("Tersisa " + productStock + " pcs");
        holder.mQuantity.setText(String.valueOf(cartPosition.getQuantity()));
        if (cartPosition.getProduct().getImages() != null) {
            if (!cartPosition.getProduct().getImages().isEmpty()) {
                Glide.with(context)
                        .load(Client.IMAGE_URL + cartPosition.getProduct().getImages().get(0).getImage_name())
                        .into(holder.mImageProduct);
            }
        }

        if (cartPosition.getProduct().getStock() < 1) {
            holder.mImageProduct.setAlpha((float) 0.5);
            holder.mOutOfStockText.setVisibility(View.VISIBLE);

            isEmptyProductStock = true;
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
                        progressbar.startProgressBarGambung();
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

        holder.mDecreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmptyProductStock()) {
                    return;
                }

                int q = Integer.parseInt(String.valueOf(holder.mQuantity.getText()));

                if (q == 1) {
                    Toast.makeText(v.getContext(), "Kuantitas Minimal 1", Toast.LENGTH_SHORT).show();
                    return;
                }

                q--;

                holder.mQuantity.setText(String.valueOf(q));
                cartPosition.setQuantity(q);

                String totalPrice = getTotalPrice(dataCart);
                fragment.mTotal.setText("Rp " + totalPrice + ",-");
            }
        });

        holder.mIncreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.parseInt(String.valueOf(holder.mQuantity.getText()));

                if (productStock == 0) {
                    Toast.makeText(v.getContext(), "Stok Habis", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (productStock <= q) {
                    Toast.makeText(v.getContext(), "Stok hanya tersedia " + productStock, Toast.LENGTH_SHORT).show();
                    return;
                }

                q++;

                holder.mQuantity.setText(String.valueOf(q));
                cartPosition.setQuantity(q);

                String totalPrice = getTotalPrice(dataCart);
                fragment.mTotal.setText("Rp " + totalPrice + ",-");
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataCart.size();
    }

    public List<DataCart> getDataCart() {
        return dataCart;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mNameProduct, mProductStock, mPriceProduct, mQuantity, mOutOfStockText;
        ImageView mImageProduct;
        Button mDeleteButton, mIncreaseQuantity, mDecreaseQuantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameProduct = itemView.findViewById(R.id.cartTitle);
            mProductStock = itemView.findViewById(R.id.productStock);
            mPriceProduct = itemView.findViewById(R.id.cartPrice);
            mImageProduct = itemView.findViewById(R.id.cartBackground);
            mDeleteButton = itemView.findViewById(R.id.deleteButton);
            mIncreaseQuantity = itemView.findViewById(R.id.increaseQty);
            mDecreaseQuantity = itemView.findViewById(R.id.decreaseQty);
            mQuantity = itemView.findViewById(R.id.quantity);
            mOutOfStockText = itemView.findViewById(R.id.outOfStockText);
        }
    }

    private void deleteCart(int id, int position) {
        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<ResponseBody> callCart = service.deleteCart(
                id
        );
        callCart.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dataCart.remove(position);
                homeActivity home = (homeActivity) context;
                Intent intent = new Intent(context, homeActivity.class);
                intent.putExtra("fragment", "cart");
                context.startActivity(intent);
                home.finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                progressbar.endProgressBarGambung();
            }
        });
    }

    private String getTotalPrice(List<DataCart> dataCarts) {
        int harga = 0;
        for (int i = 0; i < dataCarts.size(); i++) {
            harga += (dataCarts.get(i).getProduct().getPrice() * dataCarts.get(i).getQuantity());
        }
        return Integer.toString(harga);
    }

    public boolean isEmptyProductStock() {
        return isEmptyProductStock;
    }
}
