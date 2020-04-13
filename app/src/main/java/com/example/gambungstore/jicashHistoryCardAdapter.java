package com.example.gambungstore;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gambungstore.models.jicash.HistoryJicash;

import java.util.List;

public class jicashHistoryCardAdapter extends RecyclerView.Adapter<jicashHistoryCardAdapter.CardViewViewHolder> {
    private static final String TAG = "jicashHistoryCardAdapte";

    private Context context;
    private List<HistoryJicash> jicashList;

    public jicashHistoryCardAdapter(Context context, List<HistoryJicash> jicashList) {
        this.context = context;
        this.jicashList = jicashList;
    }

    @NonNull
    @Override
    public jicashHistoryCardAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jicash_history_card, parent, false);
        jicashHistoryCardAdapter.CardViewViewHolder mCardViewHolder = new jicashHistoryCardAdapter.CardViewViewHolder(view);
        return mCardViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull jicashHistoryCardAdapter.CardViewViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: "+position);
        HistoryJicash jicashPosition = jicashList.get(position);
        holder.historyTitle.setText(jicashPosition.getTransaction_type());
        if (jicashPosition.getTransaction_type().equals("Pembayaran Transaksi")){
            holder.historyAmount.setText("-Rp. "+Integer.toString(jicashPosition.getAmount()));
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
        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            historyTitle = itemView.findViewById(R.id.historyTitle);
            historyDate = itemView.findViewById(R.id.historyDate);
            historyAmount = itemView.findViewById(R.id.historyAmount);
        }
    }
}
