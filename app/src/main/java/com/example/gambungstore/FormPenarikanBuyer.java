package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Service;
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
import com.example.gambungstore.models.Bank;
import com.example.gambungstore.models.jicash.HistoryJicash;
import com.example.gambungstore.models.jicash.Jicash;
import com.example.gambungstore.models.jicash.PenarikanJicash;
import com.example.gambungstore.progressbar.ProgressBarGambung;
import com.example.gambungstore.services.Services;
import com.example.gambungstore.sharedpreference.SharedPreference;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class FormPenarikanBuyer extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "FormPenarikanBuyer";

    private EditText amount, mNomorrekening, account_name, bank_code;
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
                if (amount.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Pastikan Jumlah Jicash Field Terisi", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mNomorrekening.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Pastikan Nomor Rekening Field Terisi", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (account_name.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Pastikan Atas Nama Field Terisi", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (bank_code.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Pastikan Penyedia Jasa Field Terisi", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent intent = new Intent(getApplicationContext(), CheckoutPenarikanBuyerProses.class);
                    uploadPenarikanJicash();
                    //Membuat obyek bundle
                    Bundle b = new Bundle();

                    //Menyisipkan tipe data String ke dalam obyek bundle
                    b.putString("jicash", amount.getText().toString());
                    b.putString("noreq", mNomorrekening.getText().toString());
                    b.putString("atasnama", account_name.getText().toString());
                    b.putString("penyediajasa", bank_code.getText().toString());

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
        this.amount = findViewById(R.id.jumlahjicash);
        this.mNomorrekening = findViewById(R.id.nomorrekening);
        this.account_name = findViewById(R.id.atasnama);
        this.bank_code = findViewById(R.id.penyediajasa);
    }

    public void uploadPenarikanJicash(){
        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<ResponseBody> uploadPenarikanJicash = service.uploadPenarikanJicash(
                //disini belom
                this.amount.getText().toString(),
                this.account_name.getText().toString(),
                this.bank_code.getText().toString()
                //ussername sama account number belom
        );
        uploadPenarikanJicash.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getApplicationContext(), "Bissmillah", Toast.LENGTH_SHORT).show();
                Log.d(String.valueOf(FormPenarikanBuyer.this), "onResponse: " +response.raw());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }

    public void getBank(){
        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<Bank> callBank = service.getBank();
        callBank.enqueue(new Callback<Bank>() {
            @Override
            public void onResponse(Call<Bank> call, Response<Bank> response) {
                //belom bisa
                List<Bank> callBank =response.body().getBank();

            }

            @Override
            public void onFailure(Call<Bank> call, Throwable t) {

            }
        });
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