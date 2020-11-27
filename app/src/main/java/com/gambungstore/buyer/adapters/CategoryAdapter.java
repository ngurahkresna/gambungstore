package com.gambungstore.buyer.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gambungstore.buyer.R;
import com.gambungstore.buyer.models.category.DataCategory;
import com.gambungstore.buyer.productActivity;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private static final String TAG = "CategoryAdapter";
    
    private List<DataCategory> category;
    private Context context;
    private String flag;

    public CategoryAdapter(List<DataCategory> category, Context context, String flag) {
        this.category = category;
        this.context = context;
        this.flag = flag;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = null;
        if (this.flag.equals("CategoryActivity")) {
            mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_category_width, parent, false);
        }else {
            mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_category, parent, false);
        }
        CategoryAdapter.MyViewHolder mViewHolder = new CategoryAdapter.MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataCategory categoryPosition = category.get(position);
        holder.mCategoryWidth.setText(categoryPosition.getName());

        holder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, productActivity.class);
                intent.putExtra("status", "category");
                intent.putExtra("key", categoryPosition.getCode());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mCategoryWidth;
        RelativeLayout mCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            if (flag.equals("CategoryActivity")) {
                this.mCategoryWidth = itemView.findViewById(R.id.categoryWidth);
                this.mCard = itemView.findViewById(R.id.categoryCardWidth);
            }else{
                this.mCategoryWidth = itemView.findViewById(R.id.categoryTitle);
                this.mCard = itemView.findViewById(R.id.categoryCardTitle);
            }
        }
    }
}
