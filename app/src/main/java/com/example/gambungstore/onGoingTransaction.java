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
public class onGoingTransaction extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    PagerAdapter pagerAdapter;
    public onGoingTransaction() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_on_going_transaction, container, false);
    }
    public void transactionAdapter() {
        recyclerView = getView().findViewById(R.id.onGoingRecyclerView);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        OnGoingAdapter onGoingAdapter = new OnGoingAdapter();
        recyclerView.setAdapter(onGoingAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        transactionAdapter();
    }
  }
