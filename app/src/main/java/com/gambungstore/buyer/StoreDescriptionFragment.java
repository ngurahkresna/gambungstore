package com.gambungstore.buyer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gambungstore.buyer.models.Store;

public class StoreDescriptionFragment extends Fragment {

    private Store store;

    private TextView mDescription;
    private TextView mAddress;
    private TextView mSellerName;
    private TextView mSellerPhone;

    public StoreDescriptionFragment(Store store) {
        this.store = store;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_store_description, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mDescription = getView().findViewById(R.id.description);
        mAddress = getView().findViewById(R.id.address);
        mSellerName = getView().findViewById(R.id.seller_name);
        mSellerPhone = getView().findViewById(R.id.seller_phone);

        mDescription.setText(store.getDescription());
        mAddress.setText(store.getAddress());
        mSellerName.setText(store.getUser().getName());
        mSellerPhone.setText(store.getUser().getPhone());
    }
}
