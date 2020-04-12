package com.example.gambungstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class jicashHistoryCardAdapter extends RecyclerView.Adapter<jicashHistoryCardAdapter.CardViewViewHolder> {

    public jicashHistoryCardAdapter(){

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

    }

    @Override
    public int getItemCount() {
        return 1;
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
