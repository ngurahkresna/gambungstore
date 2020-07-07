package com.example.gambungstore;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
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

public class FormPenarikanBuyer extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText mJumlahjicash, mNomorrekening, mAtasnama, mPenyediajasa;
    private ImageView mBackArrow;
    private Button mButtonKonfirmasiFormPenarikan;
    RecyclerView rvHistory;
    LinearLayoutManager linearLayoutManager;
    TextView jicashBalance;
    Spinner spinBank;

    String filter = "Semua";
    String from_date = null, until_date = null;
    int counter = 0;

    private ProgressBarGambung progressBarGambung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_penarikan_buyer);

        Spinner spinBank = findViewById(R.id.spinnerbar1);
        if (spinBank != null) {
            spinBank.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.nama_bank, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        if (spinBank != null) {
            spinBank.setAdapter(adapter);
        }

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
                    Intent intent = new Intent(getApplicationContext(), CheckoutPenarikanBuyerProses.class);
                    //Membuat obyek bundle
                    Bundle b = new Bundle();

                    //Menyisipkan tipe data String ke dalam obyek bundle
                    b.putString("jicash", mJumlahjicash.getText().toString());
                    b.putString("noreq", mNomorrekening.getText().toString());
                    b.putString("atasnama", mAtasnama.getText().toString());

                    //Menambahkan bundle intent
                    intent.putExtras(b);

                    //memulai Activity kedua
                    startActivity(intent);
                    progressBarGambung.startProgressBarGambung();
                }
            }
        });
        progressBarGambung.endProgressBarGambung();
    }

    private void getXml() {
        this.mJumlahjicash = findViewById(R.id.jumlahjicash);
        this.mNomorrekening = findViewById(R.id.nomorrekening);
        this.mAtasnama = findViewById(R.id.atasnama);
        this.mPenyediajasa = findViewById(R.id.penyediajasa);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#388A6B"));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.nama_bank, android.R.layout.simple_spinner_item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}