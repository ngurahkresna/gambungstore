package com.example.gambungstore;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link onGoingTransaction.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link onGoingTransaction#newInstance} factory method to
 * create an instance of this fragment.
 */
public class historyTransaction extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    PagerAdapter pagerAdapter;
    public historyTransaction() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_history_transaction, container, false);
    }
    public void transactionAdapter() {
        recyclerView = getView().findViewById(R.id.historyRecyclerView);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        HistoryAdapter HistoryAdapter = new HistoryAdapter();
        recyclerView.setAdapter(HistoryAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        transactionAdapter();
    }
}
