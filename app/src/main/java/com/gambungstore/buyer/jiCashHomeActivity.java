package com.gambungstore.buyer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gambungstore.buyer.client.Client;
import com.gambungstore.buyer.models.jicash.HistoryJicash;
import com.gambungstore.buyer.models.jicash.Jicash;
import com.gambungstore.buyer.progressbar.ProgressBarGambung;
import com.gambungstore.buyer.services.Services;
import com.gambungstore.buyer.sharedpreference.SharedPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class jiCashHomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "jiCashHomeActivity";
    RecyclerView rvHistory;
    LinearLayoutManager linearLayoutManager;
    ImageView backView;
    Button btnTopup, btnPeriode;
    TextView jicashBalance;
    ProgressBarGambung progressBar;

    String filter = "Semua";
    String from_date = null, until_date = null;
    int counter = 0;
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
                startActivityForResult(intent,1);
            }
        });
        getData();
    }

    public void historyCardAdapter(List<HistoryJicash> jicashList){
        rvHistory = findViewById(R.id.jicashHistoyRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL, false);
        rvHistory.setLayoutManager(linearLayoutManager);
        jicashHistoryCardAdapter jicashHistoryCardAdapter = new jicashHistoryCardAdapter(this,jicashList,this.filter,this.from_date,this.until_date);
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
                if (response.body().size()!=0) {
                    historyCardAdapter(response.body().get(0).getHistory());
                    jicashBalance.setText("Rp. " + response.body().get(0).getBalance() + ",-");

                }
                else{
                    jicashBalance.setText("Rp. 0");
                }
                progressBar.endProgressBarGambung();
            }

            @Override
            public void onFailure(Call<List<Jicash>> call, Throwable t) {
                Toast.makeText(jiCashHomeActivity.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                progressBar.endProgressBarGambung();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#388A6B"));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.transaksi_jicash, android.R.layout.simple_spinner_item);
        Log.d(TAG, "onItemSelected: " + adapter.getItem(position));
        if (this.counter++ != 0){
            progressBar.startProgressBarGambung();
            this.filter = adapter.getItem(position).toString();
            this.getData();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if(resultCode == RESULT_OK) {
                this.from_date = data.getStringExtra("from_date");
                this.until_date = data.getStringExtra("until_date");
                this.getData();
            }
        }
    }
}
