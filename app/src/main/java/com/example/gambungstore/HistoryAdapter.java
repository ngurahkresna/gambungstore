package com.example.gambungstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.gambungstore.models.transaction.DataOnGoing;
import com.example.gambungstore.models.transaction.DataTransaction;
import com.example.gambungstore.models.transaction.OnGoing;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    public Context context;
    private List<DataTransaction> transactionList;

    public HistoryAdapter(Context context, List<DataTransaction> transactionList) {
        this.context = context;
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_history_transaction, viewGroup, false);
        HistoryAdapter.HistoryViewHolder mHistoryViewHolder = new HistoryAdapter.HistoryViewHolder(view);
        return mHistoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder holder, int position) {
        DataTransaction transactionPosition = transactionList.get(position);

        if(transactionPosition.getDetailTransaction().getHistory().isEmpty()){
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            return;
        }

        holder.tvTanggal.setText(transactionPosition.getTanggal().toString());
        holder.tvProduk.setText(transactionPosition.getProduct().getName().toString());
        holder.tvHarga.setText(String.valueOf(transactionPosition.getProduct().getPrice()));
        holder.tvQty.setText("("+String.valueOf(transactionPosition.getQuantity())+"pcs)");

        if(transactionPosition.getDetailTransaction().getHistory().get(0).getStatus().equals("accepted")){
            holder.tvStatus.setText("DITERIMA");
        }else{
            holder.tvStatus.setText("DITOLAK");
        }

        int total = transactionPosition.getProduct().getPrice()*transactionPosition.getQuantity();
        holder.tvTotal.setText(String.valueOf(total)+",-");

    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduk, imgCourier;
        TextView tvTanggal, tvProduk, tvHarga, tvQty, tvStatus, tvInvoice, tvTotal;
        Button btnConfirm, btnCancel, btnSelesai;
        LinearLayout card;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduk = itemView.findViewById(R.id.transactionBackground);
            imgCourier = itemView.findViewById(R.id.imgCourier);
            tvTanggal = itemView.findViewById(R.id.transaction_date);
            tvProduk = itemView.findViewById(R.id.transactionTitle);
            tvHarga = itemView.findViewById(R.id.transactionPriceHistory);
            tvQty = itemView.findViewById(R.id.transactionQuantityHistory);
            tvStatus = itemView.findViewById(R.id.statusTransaction);
            tvInvoice = itemView.findViewById(R.id.invoiceTransaction);
            btnConfirm = itemView.findViewById(R.id.confirmButton);
            btnCancel = itemView.findViewById(R.id.cancelButton);
            btnSelesai = itemView.findViewById(R.id.doneButton);
            card = itemView.findViewById(R.id.card_history_transaction);
            tvTotal = itemView.findViewById(R.id.transactionTotal);
        }
    }
}
