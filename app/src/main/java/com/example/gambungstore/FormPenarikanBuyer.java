package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.Bank;
import com.example.gambungstore.models.ResultBank;
import com.example.gambungstore.progressbar.ProgressBarGambung;
import com.example.gambungstore.services.Services;
import com.example.gambungstore.sharedpreference.SharedPreference;

import java.util.ArrayList;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormPenarikanBuyer extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "FormPenarikanBuyer";

    private EditText amount, mNomorrekening, account_name, mPenyediajasa;
    private ImageView mBackArrow;
    private Button mButtonKonfirmasiFormPenarikan;
    RecyclerView rvHistory;
    LinearLayoutManager linearLayoutManager;
    TextView jicashBalance;
    Spinner spinBank;
    private String id;
    private String bank_code;
    private String created_at;
    private String update_at;

    String filter = "Semua";
    String from_date = null, until_date = null;
    int counter = 0;

    private ProgressBarGambung progressBarGambung;

    private ArrayList<String> SpinnerNameBank = new ArrayList<>();
    private ArrayList<Integer> SpinnerIdBank = new ArrayList<>();
    private ArrayList<Integer> SpinnerCodeBank = new ArrayList<>();
    private ArrayList<Date> SpinnerCreatedBank = new ArrayList<>();
    private ArrayList<Date>  SpinnerUpdatedBank = new ArrayList<>();
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
        this.getBank();

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
                if (mPenyediajasa.getText().toString().isEmpty()) {
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
                    b.putString("penyediajasa", mPenyediajasa.getText().toString());

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

    private void uploadPenarikanJicash() {

    }

    private void getXml() {
        this.amount = findViewById(R.id.jumlahjicash);
        this.mNomorrekening = findViewById(R.id.nomorrekening);
        this.account_name = findViewById(R.id.atasnama);
        this.mPenyediajasa = findViewById(R.id.penyediajasa);
    }

    public void uploadPenarikanJicash(String username , int amount, int account_no, String account_name, int bank_code){
        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<ResponseBody> uploadPenarikanJicash = service.uploadPenarikanJicash(
                //disini belom
               username,
                amount,
                account_no,
                account_name,
                bank_code


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
        Call<Bank> dataBank = service.getBank();
        dataBank.enqueue(new Callback<Bank>() {
            @Override
            public void onResponse(Call<Bank> call, Response<Bank> response) {
                Bank BankName = response.body();
                for (ResultBank rs : BankName.getBanks().getResultBanks()){
                    SpinnerNameBank.add(rs.getBank_name());
                    SpinnerIdBank.add(rs.getId());
                    SpinnerCodeBank.add(rs.getBank_code());
                    SpinnerCreatedBank.add(rs.getCreated_at());
                    SpinnerUpdatedBank.add(rs.getUpdate_at());
                }
            }
            @Override
            public void onFailure(Call<Bank> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }
    public void showBank(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FormPenarikanBuyer.this);
        builder.setTitle("Pilih Bank");
        builder.setItems(SpinnerNameBank.toArray(new String[0]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPenyediajasa.setText(SpinnerNameBank.get(which));
                id = String.valueOf(SpinnerIdBank.get(which));
                bank_code = String.valueOf(SpinnerCodeBank.get(which));
                created_at = String.valueOf(SpinnerCreatedBank.get(which));
                update_at = String.valueOf(SpinnerUpdatedBank.get(which));
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
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