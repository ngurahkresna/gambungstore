package com.gambungstore.buyer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gambungstore.buyer.models.jicash.HistoryJicash;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;

public class jicashHistoryCardAdapter extends RecyclerView.Adapter<jicashHistoryCardAdapter.CardViewViewHolder> {
    private static final String TAG = "jicashHistoryCardAdapte";

    private Context context;
    private List<HistoryJicash> jicashList;
    private String filter, from_date, until_date;

    public jicashHistoryCardAdapter(Context context, List<HistoryJicash> jicashList,String filter,String from_date, String until_date) {
        this.context = context;
        this.jicashList = jicashList;
        this.filter = filter;
        this.from_date = from_date;
        this.until_date = until_date;
    }

    @NonNull
    @Override
    public jicashHistoryCardAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jicash_history_card, parent, false);
        jicashHistoryCardAdapter.CardViewViewHolder mCardViewHolder = new jicashHistoryCardAdapter.CardViewViewHolder(view);
        return mCardViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull jicashHistoryCardAdapter.CardViewViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: "+position);
        HistoryJicash jicashPosition = jicashList.get(position);

        if (filter.equals("Top Up Ji-Cash")){
            if (jicashPosition.getTransaction_type().equals("Pembayaran Transaksi") || jicashPosition.getTransaction_type().equals("Refund")){
                holder.cardView.setVisibility(View.GONE);
                holder.cardView.getLayoutParams().height = 0;
            }
        }else if(filter.equals("Pemakaian Ji-Cash")){
            if (jicashPosition.getTransaction_type().equals("Topup") || jicashPosition.getTransaction_type().equals("Refund")){
                holder.cardView.setVisibility(View.GONE);
                holder.cardView.getLayoutParams().height = 0;
            }
        }else if(filter.equals("Refund Ji-Cash")){
            if (jicashPosition.getTransaction_type().equals("Topup") || jicashPosition.getTransaction_type().equals("Pembayaran Transaksi")){
                holder.cardView.setVisibility(View.GONE);
                holder.cardView.getLayoutParams().height = 0;
            }
        }

        if (from_date != null && until_date != null){
            Log.d(TAG, "onBindViewHolder: "+from_date+" "+until_date);
            Date from, until;
            try {
                from = new SimpleDateFormat("yyyy-MM-dd").parse(from_date);
                until = new SimpleDateFormat("yyyy-MM-dd").parse(until_date);
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(jicashPosition.getDate());
                if (!((date.before(until) || date.equals(until))&& (date.after(from) || date.equals(from)))) {
                    holder.cardView.setVisibility(View.GONE);
                    holder.cardView.getLayoutParams().height = 0;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (jicashPosition.getTransaction_type().equals("Topup")){
            Log.d(TAG, "onBindViewHolder: disini");
            if (jicashPosition.getIsApproved().equals("OPTNO")){
                Log.d(TAG, "onBindViewHolder: optno"+jicashPosition.getTopup_image());
                if (isNull(jicashPosition.getTopup_image())){
                    holder.historyTitle.setText("Topup - Bukti Upload Ditolak");
                    holder.cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context,CheckoutPayment.class);
                            intent.putExtra("jicash","jicash");
                            intent.putExtra("productPrice",jicashPosition.getAmount());
                            intent.putExtra("discountPrice",0);
                            intent.putExtra("grandTotalPrice",jicashPosition.getAmount());
                            intent.putExtra("expeditionPrice",0);
                            intent.putExtra("created_at",jicashPosition.getDate());
                            intent.putExtra("updateProofJicash","update");
                            intent.putExtra("jicash_id",jicashPosition.getId());
                            context.startActivity(intent);
                        }
                    });
                }else{
                    holder.historyTitle.setText("Topup - Sedang Proses");
                }
            }
        }else{
            holder.historyTitle.setText(jicashPosition.getTransaction_type());
        }

        if (jicashPosition.getTransaction_type().equals("Pembayaran Transaksi")){
            holder.historyAmount.setText("-Rp. "+Integer.toString(jicashPosition.getAmount()));
            holder.historyAmount.setTextColor(Color.parseColor("#D93E3E"));
        }else{
            holder.historyAmount.setText("+Rp. "+Integer.toString(jicashPosition.getAmount()));
        }
        holder.historyDate.setText(jicashPosition.getDate());
    }

    @Override
    public int getItemCount() {
        return this.jicashList.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView historyTitle, historyDate, historyAmount;
        CardView cardView;
        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewHistory);
            historyTitle = itemView.findViewById(R.id.historyTitle);
            historyDate = itemView.findViewById(R.id.historyDate);
            historyAmount = itemView.findViewById(R.id.historyAmount);
        }
    }
}
