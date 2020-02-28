package com.example.gambungstore.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gambungstore.R;
import com.example.gambungstore.models.Category.Category;
import com.example.gambungstore.models.Category.DataCategory;

import java.util.ArrayList;
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
    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mCategoryWidth;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            if (flag.equals("CategoryActivity")) {
                this.mCategoryWidth = itemView.findViewById(R.id.categoryWidth);
            }else{
                this.mCategoryWidth = itemView.findViewById(R.id.categoryTitle);
            }
        }
    }
}
