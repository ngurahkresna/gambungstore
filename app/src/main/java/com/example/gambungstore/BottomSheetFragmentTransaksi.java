package com.example.gambungstore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomSheetFragmentTransaksi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomSheetFragmentTransaksi extends BottomSheetDialogFragment {

    public BottomSheetFragmentTransaksi() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_sheet_transaksi, container, false);
    }
}
