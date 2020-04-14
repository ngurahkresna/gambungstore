package com.example.gambungstore.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gambungstore.CheckoutForm;
import com.example.gambungstore.MainActivity;
import com.example.gambungstore.R;
import com.example.gambungstore.cartFragment;
import com.example.gambungstore.models.cart.DataCart;
import com.example.gambungstore.models.product.DataProduct;
import com.example.gambungstore.models.store.DataStore;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ExpeditionCheckoutAdapter extends RecyclerView.Adapter<ExpeditionCheckoutAdapter.MyViewHolder> {

    private List<DataStore> dataStores;
    public Context context;

    public ExpeditionCheckoutAdapter(List<DataStore> dataStores, Context context) {
        this.dataStores = dataStores;
        this.context = context;
    }

    @NonNull
    @Override
    public ExpeditionCheckoutAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expedition_card_checkout, parent, false);
        ExpeditionCheckoutAdapter.MyViewHolder mViewHolder = new ExpeditionCheckoutAdapter.MyViewHolder(mView);
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ExpeditionCheckoutAdapter.MyViewHolder holder, int position) {
        final DataStore storePosition = dataStores.get(position);
        productAdapterCheckout(holder, storePosition.getProducts(), position);

        holder.mExpeditionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> expedition = new ArrayList<>();
                ArrayList<Integer> cost = new ArrayList<>();
                ArrayList<String> code = new ArrayList<>();
                for (int i = 0; i < storePosition.getExpeditions().size(); i++){
                    for (int j = 0; j < storePosition.getExpeditions().get(i).getPrice().size(); j++){
                        expedition.add(storePosition.getExpeditions().get(i).getExpedition_code().toUpperCase()+" - "+
                                storePosition.getExpeditions().get(i).getPrice().get(j).getService()+" ( Rp "+
                                storePosition.getExpeditions().get(i).getPrice().get(j).getCost().get(0).getValue()+",- ) - "+
                                storePosition.getExpeditions().get(i).getPrice().get(j).getCost().get(0).getDay()+" hari");
                        cost.add(storePosition.getExpeditions().get(i).getPrice().get(j).getCost().get(0).getValue());
                        code.add(storePosition.getExpeditions().get(i).getExpedition_code());
                    }
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Pilih Ekspedisi");

                builder.setItems(expedition.toArray(new String[0]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        holder.mExpeditionChoosen.setText(expedition.get(which));
                        ((CheckoutForm)context).setExpedition(cost.get(which), position, code.get(which));
                        ((CheckoutForm)context).refreshRincianHarga();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataStores.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RecyclerView product;
        TextView mExpeditionChoosen;
        Button mExpeditionButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product = itemView.findViewById(R.id.product);
            mExpeditionChoosen = itemView.findViewById(R.id.expeditionChoosed);
            mExpeditionButton = itemView.findViewById(R.id.buttonExpedition);
        }
    }

    public void productAdapterCheckout(@NonNull ExpeditionCheckoutAdapter.MyViewHolder holder, List<DataProduct> listProducts, int position) {
        ProductCheckoutAdapter productAdapter = new ProductCheckoutAdapter(listProducts,context,position);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        holder.product.setLayoutManager(linearLayoutManager);
        holder.product.setAdapter(productAdapter);
    }
}
