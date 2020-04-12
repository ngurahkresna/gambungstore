package com.example.gambungstore.adapters;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.gambungstore.StoreDescriptionFragment;
import com.example.gambungstore.StoreProductsFragment;
import com.example.gambungstore.models.Store;
import com.example.gambungstore.models.product.DataProduct;

import java.util.List;

public class StorePagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private Store store;
    private List<DataProduct> products;

    public StorePagerAdapter(FragmentManager fm, int NumOfTabs, Store store, List<DataProduct> products) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.store = store;
        this.products = products;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new StoreDescriptionFragment(store);
            case 1:
                Log.d("ADAPTER", String.valueOf(products.size()));
                return new StoreProductsFragment(products);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    public void setProducts(List<DataProduct> products) {
        this.products = products;
    }
}
