package com.example.gambungstore;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.transaction.DataOnGoing;
import com.example.gambungstore.models.transaction.OnGoing;
import com.example.gambungstore.services.Services;
import com.example.gambungstore.sharedpreference.SharedPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
