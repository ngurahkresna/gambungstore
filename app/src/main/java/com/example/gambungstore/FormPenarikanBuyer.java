package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class FormPenarikanBuyer extends AppCompatActivity {

    private EditText mJumlahjicash, mNomorrekening, mAtasnama, mPenyediajasa;
    private ImageView mBackArrow;
    private Button mButtonKonfirmasiFormPenarikan;
    RecyclerView rvHistory;
    LinearLayoutManager linearLayoutManager;
    TextView jicashBalance;

    String filter = "Semua";
    String from_date = null, until_date = null;
    int counter = 0;

    private ProgressBarGambung progressBarGambung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_penarikan_buyer);

        jicashBalance = findViewById(R.id.jicashBalance);

        progressBarGambung = new ProgressBarGambung(this);
        progressBarGambung.startProgressBarGambung();

        this.getXml();

        mBackArrow = findViewById(R.id.backArrow);
        mBackArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(FormPenarikanBuyer.this, jiCashHomeActivity.class);
                startActivity(intent);
                progressBarGambung.startProgressBarGambung();
            }
        });

        mButtonKonfirmasiFormPenarikan = findViewById(R.id.buttonKonfirmasiFormPenarikan);
        mButtonKonfirmasiFormPenarikan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mJumlahjicash.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Pastikan Jumlah Jicash Field Terisi", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mNomorrekening.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Pastikan Nomor Rekening Field Terisi", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mAtasnama.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Pastikan Atas Nama Field Terisi", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mPenyediajasa.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Pastikan Penyedia Jasa Field Terisi", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent intent = new Intent(FormPenarikanBuyer.this, CheckoutPenarikanBuyerProses.class);
                    startActivity(intent);
                    progressBarGambung.startProgressBarGambung();
                }
            }
        });
        progressBarGambung.endProgressBarGambung();
        getData();
    }

    private void getXml() {
        this.mJumlahjicash = findViewById(R.id.jumlahjicash);
        this.mNomorrekening = findViewById(R.id.nomorrekening);
        this.mAtasnama = findViewById(R.id.atasnama);
        this.mPenyediajasa = findViewById(R.id.penyediajasa);

    }

    public void historyCardAdapter(List<HistoryJicash> jicashList) {
        rvHistory = findViewById(R.id.jicashHistoyRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvHistory.setLayoutManager(linearLayoutManager);
        jicashHistoryCardAdapter jicashHistoryCardAdapter = new jicashHistoryCardAdapter(this, jicashList, this.filter, this.from_date, this.until_date);
        rvHistory.setAdapter(jicashHistoryCardAdapter);
    }

    private void getData() {
        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<List<Jicash>> callHistory = service.getJicash(
                SharedPreference.getRegisteredUsername(this)
        );
        callHistory.enqueue(new Callback<List<Jicash>>() {
            @Override
            public void onResponse(Call<List<Jicash>> call, Response<List<Jicash>> response) {
                if (response.body().size() != 0) {
                    historyCardAdapter(response.body().get(0).getHistory());
                    jicashBalance.setText("Rp. " + response.body().get(0).getBalance() + ",-");

                } else {
                    jicashBalance.setText("Rp. 0");
                }
                progressBarGambung.endProgressBarGambung();
            }

            @Override
            public void onFailure(Call<List<Jicash>> call, Throwable t) {

            }
        });
    }
}