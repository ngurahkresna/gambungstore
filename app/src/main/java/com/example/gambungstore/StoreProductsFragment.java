package com.example.gambungstore;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gambungstore.adapters.ProductAdapter;
import com.example.gambungstore.models.Store;
import com.example.gambungstore.models.product.DataProduct;

import java.util.List;

public class StoreProductsFragment extends Fragment {

    private List<DataProduct> products;
    private RecyclerView mProducts;

    public StoreProductsFragment(List<DataProduct> products) {
        Log.d("product", String.valueOf(products.size()));
        this.products = products;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_store_products, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mProducts = getView().findViewById(R.id.products);

        ProductAdapter productAdapter = new ProductAdapter(products, getContext());

        mProducts.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mProducts.setAdapter(productAdapter);
    }
}
