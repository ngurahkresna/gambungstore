package com.example.gambungstore;

import android.content.Context;
import android.net.Uri;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link onGoingTransaction.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link onGoingTransaction#newInstance} factory method to
 * create an instance of this fragment.
 */
public class historyTransaction extends Fragment {
    private static final String TAG = "historyTransaction";
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    PagerAdapter pagerAdapter;

    ProgressBarGambung progressbar;

    public historyTransaction() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_history_transaction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        progressbar = new ProgressBarGambung(getActivity());
        progressbar.startProgressBarGambung();
        int id = SharedPreference.getRegisteredId(getContext());
        if (id != 0){
            getData();
        }else{
            progressbar.endProgressBarGambung();
        }
    }

    public void transactionAdapter(List<DataTransaction> transactionList) {
        recyclerView = getView().findViewById(R.id.historyRecyclerView);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        HistoryAdapter HistoryAdapter = new HistoryAdapter(getContext(),transactionList);
        recyclerView.setAdapter(HistoryAdapter);
    }

    private void getData(){
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
