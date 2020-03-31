package com.example.gambungstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.gambungstore.models.transaction.DataOnGoing;
import com.example.gambungstore.models.transaction.OnGoing;

import java.util.ArrayList;
import java.util.List;

public class OnGoingAdapter extends RecyclerView.Adapter<OnGoingAdapter.OnGoingViewHolder> {
    private List<DataOnGoing> dataOnGoings;
    public Context context;

    public OnGoingAdapter(){

    }
    @NonNull
    @Override
    public OnGoingAdapter.OnGoingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_on_going_transaction,viewGroup, false);
        OnGoingAdapter.OnGoingViewHolder mOnGoingViewHolder = new OnGoingAdapter.OnGoingViewHolder(view);
        return mOnGoingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OnGoingAdapter.OnGoingViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class OnGoingViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduk, imgCourier;
        TextView tvTanggal, tvProduk, tvHarga, tvQty, tvStatus, tvInvoice;
        Button btnConfirm, btnCancel, btnSelesai;

        public OnGoingViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduk = itemView.findViewById(R.id.transactionBackground);
            imgCourier = itemView.findViewById(R.id.imgCourier);
            tvTanggal = itemView.findViewById(R.id.transaction_date);
            tvProduk = itemView.findViewById(R.id.transactionTitle);
            tvHarga = itemView.findViewById(R.id.productPrice);
            tvQty = itemView.findViewById(R.id.transactionQuantity);
            tvStatus = itemView.findViewById(R.id.statusTransaction);
            tvInvoice = itemView.findViewById(R.id.invoiceTransaction);
            btnConfirm = itemView.findViewById(R.id.confirmButton);
            btnCancel = itemView.findViewById(R.id.cancelButton);
            btnSelesai = itemView.findViewById(R.id.doneButton);
        }
    }
}
