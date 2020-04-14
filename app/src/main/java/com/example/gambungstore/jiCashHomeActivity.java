package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.jicash.HistoryJicash;
import com.example.gambungstore.models.jicash.Jicash;
import com.example.gambungstore.progressbar.ProgressBarGambung;
import com.example.gambungstore.services.Services;
import com.example.gambungstore.sharedpreference.SharedPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class jiCashHomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RecyclerView rvHistory;
    LinearLayoutManager linearLayoutManager;
    ImageView backView;
    Button btnTopup, btnPeriode;
    TextView jicashBalance;
    ProgressBarGambung progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ji_cash_home);

        progressBar = new ProgressBarGambung(this);
        progressBar.startProgressBarGambung();

        Spinner spinner = findViewById(R.id.spinnerbar);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.transaksi_jicash, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        if (spinner != null) {
            spinner.setAdapter(adapter);
        }

        jicashBalance = findViewById(R.id.jicashBalance);

        btnTopup = findViewById(R.id.topupjicash);
        btnTopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(jiCashHomeActivity.this, TopUpJicash.class);
                startActivity(intent);
            }
        });

        backView = findViewById(R.id.backButtonJiCash);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnPeriode = findViewById(R.id.periodeButton);
        btnPeriode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(jiCashHomeActivity.this, periodeActivity.class);
                startActivity(intent);
            }
        });
        getData();
    }

    public void historyCardAdapter(List<HistoryJicash> jicashList){
        rvHistory = findViewById(R.id.jicashHistoyRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL, false);
        rvHistory.setLayoutManager(linearLayoutManager);
        jicashHistoryCardAdapter jicashHistoryCardAdapter = new jicashHistoryCardAdapter(this,jicashList);
        rvHistory.setAdapter(jicashHistoryCardAdapter);
    }

    private void getData(){
        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<List<Jicash>>  callHistory = service.getJicash(
                SharedPreference.getRegisteredUsername(this)
        );
        callHistory.enqueue(new Callback<List<Jicash>>() {
            @Override
            public void onResponse(Call<List<Jicash>> call, Response<List<Jicash>> response) {
                historyCardAdapter(response.body().get(0).getHistory());
                jicashBalance.setText("Rp. "+response.body().get(0).getBalance()+",-");
                progressBar.endProgressBarGambung();

            }

            @Override
            public void onFailure(Call<List<Jicash>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#388A6B"));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.transaksi_jicash, android.R.layout.simple_spinner_item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
