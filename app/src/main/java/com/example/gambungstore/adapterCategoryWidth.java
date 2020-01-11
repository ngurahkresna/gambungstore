package com.example.gambungstore;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class adapterCategoryWidth extends RecyclerView.Adapter<adapterCategoryWidth.MyViewHolder> {
    private String[] mDataset = {"Makanan", "Minuman", "Kerajinan", "Lihat Semua"};

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;

        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.categoryWidth);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public adapterCategoryWidth() {
//        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public adapterCategoryWidth.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_category_width, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(mDataset[position]);

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.textView.getText().equals("Lihat Semua")){
                    view.getContext().startActivity(new Intent(view.getContext(), categoryActivity.class));
                }
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.d("tes", "tes");
        return mDataset.length;
    }
}
