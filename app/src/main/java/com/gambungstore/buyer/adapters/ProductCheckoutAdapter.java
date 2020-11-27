package com.gambungstore.buyer.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gambungstore.buyer.CheckoutForm;
import com.gambungstore.buyer.R;
import com.gambungstore.buyer.client.Client;
import com.gambungstore.buyer.models.product.DataProduct;
import com.gambungstore.buyer.sharedpreference.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductCheckoutAdapter extends RecyclerView.Adapter<ProductCheckoutAdapter.MyViewHolder> {
    private static final String TAG = "ProductCheckoutAdapter";
    
    private List<DataProduct> dataProducts;
    public Context context;
    private int pos;
    private ArrayList<Integer> arrayPos = new ArrayList<>();

    public ProductCheckoutAdapter(List<DataProduct> dataProducts, Context context, int position) {
        this.dataProducts = dataProducts;
        this.context = context;
        this.pos = position;
    }

    @NonNull
    @Override
    public ProductCheckoutAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_checkout, parent, false);
        ProductCheckoutAdapter.MyViewHolder mViewHolder = new ProductCheckoutAdapter.MyViewHolder(mView);
        return mViewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull ProductCheckoutAdapter.MyViewHolder holder, int position) {
        final DataProduct productPosition = dataProducts.get(position);

        Log.d(TAG, "onBindViewHolder: "+productPosition.getCarts().size());
        if (productPosition.getCarts().size() != 0){
            for (int i = 0; i < productPosition.getCarts().size(); i++){
                if (productPosition.getCarts().get(i).getUsername().equals(SharedPreference.getRegisteredUsername(context))){
                    holder.mProductName.setText(productPosition.getName());
                    holder.mProductPrice.setText("Rp "+Integer.toString(productPosition.getPrice())+",-");
                    holder.mStoreName.setText("");
                    if (productPosition.getImages() != null) {
                        if (!productPosition.getImages().isEmpty()) {
                            Glide.with(context).load(Client.IMAGE_URL + productPosition.getImages().get(0).getImage_name()).into(holder.mProductImage);
                        }
                    }
                    holder.mMessage.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            ((CheckoutForm)context).getMessage(s.toString(), pos);

                            if (null != holder.mMessage.getLayout() && holder.mMessage.getLayout().getLineCount() > 5) {
                                holder.mMessage.getText().delete(holder.mMessage.getText().length() - 1, holder.mMessage.getText().length());
                            }
                        }
                    });

                    holder.mMessage.setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            if (holder.mMessage.getText().length() < 1) {
                                return keyCode != KeyEvent.KEYCODE_DEL;
                            }

                            return false;
                        }
                    });
                }
            }
        }else{
            holder.mProduct.setVisibility(View.GONE);
            holder.mProductComment.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return dataProducts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mProductImage;
        TextView mProductName;
        TextView mProductPrice;
        TextView mStoreName;
        LinearLayout mProduct,mProductComment;
        EditText mMessage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mProductImage = itemView.findViewById(R.id.productImage);
            mProductName = itemView.findViewById(R.id.ProductName);
            mProductPrice = itemView.findViewById(R.id.ProductPrice);
            mStoreName = itemView.findViewById(R.id.storeName);
            mProduct = itemView.findViewById(R.id.product);
            mProductComment = itemView.findViewById(R.id.product_comment);
            mMessage = itemView.findViewById(R.id.message);

        }
    }
}
