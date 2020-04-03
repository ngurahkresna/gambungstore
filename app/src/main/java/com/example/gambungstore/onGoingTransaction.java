package com.example.gambungstore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gambungstore.adapters.OnGoingAdapter;
import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.transaction.DataTransaction;
import com.example.gambungstore.models.transaction.Transaction;
import com.example.gambungstore.progressbar.ProgressBarGambung;
import com.example.gambungstore.services.Services;
import com.example.gambungstore.sharedpreference.SharedPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class onGoingTransaction extends Fragment {
    private static final String TAG = "onGoingTransaction";

    private ProgressBarGambung progressbar;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    public onGoingTransaction() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_on_going_transaction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        progressbar = new ProgressBarGambung(getActivity());
        getData();
    }

    private void transactionAdapter(List<DataTransaction> dataOnGoing) {
        recyclerView = getView().findViewById(R.id.onGoingRecyclerView);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        OnGoingAdapter onGoingAdapter = new OnGoingAdapter(dataOnGoing,getContext());
        recyclerView.setAdapter(onGoingAdapter);
    }

    private void getData(){

        progressbar.startProgressBarGambung();

        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<Transaction> callTransaction = service.getTransactionByUsername(
                SharedPreference.getRegisteredUsername(getContext())
        );
        callTransaction.enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                Log.d(TAG, "onResponse: "+response.raw());
                transactionAdapter(response.body().getTransactions());
                progressbar.endProgressBarGambung();
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });
    }

  }
