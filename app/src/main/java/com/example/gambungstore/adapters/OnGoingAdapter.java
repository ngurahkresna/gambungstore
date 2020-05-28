package com.example.gambungstore.adapters;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gambungstore.CheckoutPayment;
import com.example.gambungstore.OnGoingFragment;
import com.example.gambungstore.R;
import com.example.gambungstore.client.Client;
import com.example.gambungstore.homeActivity;
import com.example.gambungstore.models.transaction.DataOnGoing;
import com.example.gambungstore.models.transaction.DataTransaction;
import com.example.gambungstore.progressbar.ProgressBarGambung;
import com.example.gambungstore.services.Services;
import com.example.gambungstore.sharedpreference.SharedPreference;

import java.net.ResponseCache;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.CLIPBOARD_SERVICE;

public class OnGoingAdapter extends RecyclerView.Adapter<OnGoingAdapter.OnGoingViewHolder> {
    private static final String TAG = "OnGoingAdapter";
    private List<DataTransaction> dataOnGoings;
    public Context context;

    public OnGoingAdapter(List<DataTransaction> dataOnGoings, Context context) {
        this.dataOnGoings = dataOnGoings;
        this.context = context;
    }

    @NonNull
    @Override
    public OnGoingAdapter.OnGoingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_on_going_transaction, viewGroup, false);
        OnGoingAdapter.OnGoingViewHolder mOnGoingViewHolder = new OnGoingAdapter.OnGoingViewHolder(view);
        return mOnGoingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OnGoingAdapter.OnGoingViewHolder holder, int position) {
        final DataTransaction transactionPosition = dataOnGoings.get(position);

        holder.tvTanggal.setText(transactionPosition.getTanggal().toString());
        holder.tvProduk.setText(transactionPosition.getProduct().getName().toString());
        holder.tvHarga.setText(String.valueOf(transactionPosition.getProduct().getPrice()));
        holder.tvQty.setText("(" + String.valueOf(transactionPosition.getQuantity()) + "pcs)");
        if (transactionPosition.getShipping_no() == null) {
            holder.tvInvoice.setText("");

        } else {
            holder.constraintLayout.setVisibility(View.VISIBLE);
            holder.tvInvoice.setText(String.valueOf(transactionPosition.getShipping_no()));

        }

        if (transactionPosition.getProduct().getImages() != null) {
            if (!transactionPosition.getProduct().getImages().isEmpty()) {
                Glide.with(context)
                        .load(Client.IMAGE_URL + transactionPosition.getProduct().getImages().get(0).getImage_name())
                        .into(holder.imgProduk);
            }
        }

        if (transactionPosition.getExpedition().equals("jne")) {
            Glide.with(context)
                    .load("http://gambungstore.id/assets/img/expeditions/jne.png")
                    .into(holder.imgCourier);
        } else {
            Glide.with(context)
                    .load("http://gambungstore.id/assets/img/expeditions/tiki.png")
                    .into(holder.imgCourier);
        }

        int total = transactionPosition.getProduct().getPrice() * transactionPosition.getQuantity();
        holder.tvTransactionTotal.setText(String.valueOf(total) + ",-");

        holder.tvStatus.setText(transactionPosition.getDetailTransaction().getPayment().getUpdated_process().toString().toUpperCase());

        if (transactionPosition.getDetailTransaction().getPayment().getUpdated_process().equals("pembayaran")) {
            holder.btnSelesai.setVisibility(View.GONE);
            holder.btnConfirm.setVisibility(View.VISIBLE);
            holder.btnCancel.setVisibility(View.VISIBLE);
        } else {
            holder.btnConfirm.setVisibility(View.GONE);
            holder.btnCancel.setVisibility(View.GONE);
            holder.btnSelesai.setVisibility(View.VISIBLE);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (transactionPosition.getDetailTransaction().getPayment().getVerified_date() != null) {
                Date verifiedDate = sdf.parse(transactionPosition.getDetailTransaction().getPayment().getVerified_date());
                Date today = new Date();

                long diff = TimeUnit.DAYS.convert(today.getTime() - verifiedDate.getTime(), TimeUnit.MILLISECONDS);
                if(diff >= 1 && transactionPosition.getDetailTransaction().getPayment().getVerified_status().equals("OPTYS")) {
                    holder.tvExpiredInfo.setVisibility(View.VISIBLE);
                    holder.btnSelesai.setVisibility(View.GONE);
                    holder.btnConfirm.setVisibility(View.GONE);
                    holder.btnCancel.setVisibility(View.GONE);

                    holder.tvStatus.setText("REFUND JICASH");
                }
            } else {
                holder.tvExpiredInfo.setVisibility(View.GONE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //btn cancel
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialog(transactionPosition.getCode(), transactionPosition.getProduct().getCode(), position);
            }
        });

        //btn copy
        holder.btn_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getCode = holder.tvInvoice.getText().toString();
                CharSequence text = "Nomor resi " + getCode +" tersalin";

                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);

                String copy = holder.tvInvoice.getText().toString();

                if (!copy.equalsIgnoreCase("")){
                    ClipData clipdata = ClipData.newPlainText("text_copied", copy);
                    clipboard.setPrimaryClip(clipdata);
                }

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        //btn confirm
        holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (transactionPosition.getDetailTransaction().getPayment().getUpdated_process().equals("pembayaran")) {
                    Intent intent = new Intent(context, CheckoutPayment.class);
                    intent.putExtra("productPrice", transactionPosition.getDetailTransaction().getTotal_amount());
                    intent.putExtra("discountPrice", transactionPosition.getDetailTransaction().getDiscount_amount());
                    intent.putExtra("expeditionPrice", transactionPosition.getDetailTransaction().getShipping_charges());
                    intent.putExtra("grandTotalPrice", transactionPosition.getDetailTransaction().getGrand_total_amount());
                    intent.putExtra("transaction_code", transactionPosition.getCode());
                    intent.putExtra("created_at", transactionPosition.getDetailTransaction().getCreated_at());
                    context.startActivity(intent);
                }
            }
        });

        //btn selesai

        if (holder.tvStatus.getText().equals("VERIFIKASI") || holder.tvStatus.getText().equals("DALAM PROSES")){
            holder.btnSelesai.setVisibility(View.GONE);
        }

        holder.btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialogSelesai(transactionPosition.getCode(), transactionPosition.getProduct().getCode(), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataOnGoings.size();
    }

    public class OnGoingViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraintLayout;
        ImageView imgProduk, imgCourier;
        TextView tvTanggal, tvProduk, tvHarga, tvQty, tvStatus, tvInvoice, tvTransactionTotal, tvExpiredInfo;
        Button btnConfirm, btnCancel, btnSelesai, btn_copy;
        RelativeLayout card;

        public OnGoingViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.relativeLayoutbotomDetail);
            card = itemView.findViewById(R.id.onGoingCard);
            imgProduk = itemView.findViewById(R.id.transactionBackground);
            imgCourier = itemView.findViewById(R.id.imgCourierTransaction);
            tvTanggal = itemView.findViewById(R.id.transaction_date);
            tvProduk = itemView.findViewById(R.id.transactionTitle);
            tvHarga = itemView.findViewById(R.id.transactionPrice);
            tvQty = itemView.findViewById(R.id.transactionQuantity);
            tvStatus = itemView.findViewById(R.id.statusTransaction);
            tvInvoice = itemView.findViewById(R.id.invoiceTransaction);
            tvTransactionTotal = itemView.findViewById(R.id.transactionTotal);
            tvExpiredInfo = itemView.findViewById(R.id.expiredInfo);
            btnConfirm = itemView.findViewById(R.id.confirmButton);
            btnCancel = itemView.findViewById(R.id.cancelButton);
            btnSelesai = itemView.findViewById(R.id.doneButton);
            btn_copy = itemView.findViewById(R.id.btn_copy);
        }
    }

    private void alertdialog(String transaction_code, String product_code, int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage("Uang yang sudah dibayarkan tidak dapat ditarik kembali");
        alert.setTitle("Apakah Anda Yakin ?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ProgressBarGambung progressbar = new ProgressBarGambung((homeActivity) context);
                progressbar.startProgressBarGambung();

                Services service = Client.getClient(Client.BASE_URL).create(Services.class);
                Call<ResponseBody> callCancel = service.cancelTransaction(
                        transaction_code,
                        SharedPreference.getRegisteredUsername(context),
                        product_code

                );

                callCancel.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(context, "Berhasil Dibatalkan", Toast.LENGTH_SHORT).show();
                        dataOnGoings.remove(position);
                        homeActivity home = (homeActivity) context;
                        Intent intent = new Intent(context, homeActivity.class);
                        intent.putExtra("fragment", "transaction");
                        context.startActivity(intent);
                        home.finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

    private void alertdialogSelesai(String transaction_code, String product_code, int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage("Dengan menekan tombol selesai, maka anda menyatakan bahwa barang sudah sampai");
        alert.setTitle("Apakah Anda Yakin ?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ProgressBarGambung progressbar = new ProgressBarGambung((homeActivity) context);
                progressbar.startProgressBarGambung();

                Services service = Client.getClient(Client.BASE_URL).create(Services.class);
                Call<ResponseBody> callCancel = service.acceptTransaction(
                        transaction_code,
                        SharedPreference.getRegisteredUsername(context),
                        product_code

                );

                callCancel.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(context, "Berhasil Dikonfirmasi", Toast.LENGTH_SHORT).show();
                        dataOnGoings.remove(position);
                        homeActivity home = (homeActivity) context;
                        Intent intent = new Intent(context, homeActivity.class);
                        intent.putExtra("fragment", "transaction");
                        context.startActivity(intent);
                        home.finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
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
