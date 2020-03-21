package com.example.gambungstore;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
    PagerAdapter pagerAdapter;
    public onGoingTransaction() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        recyclerView.setAdapter(PagerAdapter);
        return inflater.inflate(R.layout.fragment_on_going_transaction, container, false);
    }
  }
