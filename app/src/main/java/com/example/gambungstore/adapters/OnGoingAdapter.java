package com.example.gambungstore.adapters;

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

import com.example.gambungstore.CheckoutPayment;
import com.example.gambungstore.R;
import com.example.gambungstore.models.transaction.DataOnGoing;
import com.example.gambungstore.models.transaction.DataTransaction;

import java.util.List;

public class OnGoingAdapter extends RecyclerView.Adapter<OnGoingAdapter.OnGoingViewHolder> {
    private List<DataTransaction> dataOnGoings;
    public Context context;

    public OnGoingAdapter(List<DataTransaction> dataOnGoings, Context context) {
        this.dataOnGoings = dataOnGoings;
        this.context = context;
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
        final DataTransaction transactionPosition = dataOnGoings.get(position);
        holder.tvTanggal.setText(transactionPosition.getTanggal().toString());
        holder.tvProduk.setText(transactionPosition.getProduct().getName().toString());
        holder.tvHarga.setText(String.valueOf(transactionPosition.getProduct().getPrice()));
        holder.tvQty.setText("("+String.valueOf(transactionPosition.getQuantity())+"pcs)");

        int total = transactionPosition.getProduct().getPrice()*transactionPosition.getQuantity();
        holder.tvTransactionTotal.setText(String.valueOf(total)+",-");

        holder.tvStatus.setText(transactionPosition.getDetailTransaction().getPayment().getUpdated_process().toString().toUpperCase());

        if (transactionPosition.getDetailTransaction().getPayment().getUpdated_process().equals("pembayaran")){
            holder.btnSelesai.setVisibility(View.GONE);
        }

        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialog();
            }
        });

        holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (transactionPosition.getDetailTransaction().getPayment().getUpdated_process().equals("pembayaran")){
                    Intent intent = new Intent(context, CheckoutPayment.class);
                    intent.putExtra("productPrice", transactionPosition.getDetailTransaction().getTotal_amount());
                    intent.putExtra("discountPrice", transactionPosition.getDetailTransaction().getDiscount_amount());
                    intent.putExtra("expeditionPrice",transactionPosition.getDetailTransaction().getShipping_charges());
                    intent.putExtra("grandTotalPrice",transactionPosition.getDetailTransaction().getGrand_total_amount());
                    intent.putExtra("transaction_code",transactionPosition.getCode());
                    intent.putExtra("created_at",transactionPosition.getDetailTransaction().getCreated_at());
                    context.startActivity(intent);
                }
            }
        });

        holder.btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "selesai", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataOnGoings.size();
    }

    public class OnGoingViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduk, imgCourier;
        TextView tvTanggal, tvProduk, tvHarga, tvQty, tvStatus, tvInvoice, tvTransactionTotal;
        Button btnConfirm, btnCancel, btnSelesai;

        public OnGoingViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduk = itemView.findViewById(R.id.transactionBackground);
            imgCourier = itemView.findViewById(R.id.imgCourier);
            tvTanggal = itemView.findViewById(R.id.transaction_date);
            tvProduk = itemView.findViewById(R.id.transactionTitle);
            tvHarga = itemView.findViewById(R.id.transactionPrice);
            tvQty = itemView.findViewById(R.id.transactionQuantity);
            tvStatus = itemView.findViewById(R.id.statusTransaction);
            tvInvoice = itemView.findViewById(R.id.invoiceTransaction);
            tvTransactionTotal = itemView.findViewById(R.id.transactionTotal);
            btnConfirm = itemView.findViewById(R.id.confirmButton);
            btnCancel = itemView.findViewById(R.id.cancelButton);
            btnSelesai = itemView.findViewById(R.id.doneButton);
        }
    }

    private void alertdialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage("Uang yang sudah dibayarkan tidak dapat ditarik kembali");
        alert.setTitle("Apakah Anda Yakin ?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }
}
