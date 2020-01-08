package com.example.gambungstore;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class adapterProduct extends RecyclerView.Adapter<adapterProduct.MyViewHolder> {
    private String[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textViewProduct;
        public TextView textViewPrice;
        public ImageView productImage;

        public MyViewHolder(View v) {
            super(v);
            textViewProduct = v.findViewById(R.id.productTitle);
            textViewPrice = v.findViewById(R.id.productPrice);
            productImage = v.findViewById(R.id.productBackground);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public adapterProduct() {
//        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public adapterProduct.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_product, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textViewProduct.setText("Nama Produk");
        holder.textViewPrice.setText("20K");
        holder.productImage.setImageResource(R.drawable.foto_produk);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.d("tes", "tes");
        return 9;
    }
}
