package com.gambungstore.buyer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gambungstore.buyer.adapters.OnGoingAdapter;
import com.gambungstore.buyer.models.transaction.DataOnGoing;

import java.util.List;

public class OnGoingFragment extends Fragment {
    private static final String TAG = "onGoingFragment";
    private RecyclerView ongoing;
    private LinearLayoutManager setLayoutManagerOnGoing;
    private OnGoingAdapter onGoingAdapter;

    public OnGoingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_on_going_transaction, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void getDataOnGoing(){

    }

    public void viewRecyclerOnGoing(List<DataOnGoing> dataOnGoings) {

    }
}
