package com.example.gambungstore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gambungstore.client.Client;
import com.example.gambungstore.progressbar.ProgressBarGambung;
import com.example.gambungstore.services.Services;

import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutPenarikanBuyerProses extends AppCompatActivity {

    private static final String TAG = "CheckoutPenarikanBuyer";

    private ImageView mBackArrow;
    private TextView mTime;
    private CountDownTimer timerClass;
    private ProgressBarGambung progressBarGambung;
    private TextView mJicashValue, mNoreqValue, mAtasnamaValue, mPenyediajasaValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_penarikan_buyer_proses);

        progressBarGambung = new ProgressBarGambung(this);
        progressBarGambung.startProgressBarGambung();

        Bundle b = getIntent().getExtras();

        TextView jicash = (TextView) findViewById(R.id.JicashValue);
        TextView noreq = (TextView) findViewById(R.id.NoreqValue);
        TextView atasnama = (TextView) findViewById(R.id.AtasnamaValue);
        TextView penyediajasa = (TextView) findViewById(R.id.PenyediajasaValue);
        Spinner penyediajasa1 = (Spinner) findViewById(R.id.spinnerbar1);


        jicash.setText(b.getCharSequence("jicash"));
        noreq.setText(b.getCharSequence("noreq"));
        atasnama.setText(b.getCharSequence("atasnama"));
        penyediajasa.setText(b.getCharSequence("penyediajasa"));



        mTime = findViewById(R.id.time);
        timerClass = new CountDownTimer(60000 * 60 *24, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String waktu = String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                mTime.setText("" +waktu);
            }

            @Override
            public void onFinish() {
                ///Berjalan saat waktu telah selesai atau berhenti
                Toast.makeText(CheckoutPenarikanBuyerProses.this, "Waktu Telah Berakhir", Toast.LENGTH_LONG).show();
                mTime.setText("00:00:00");
                finish();
            }
        }.start() ;

        mBackArrow = findViewById(R.id.backArrow);
        mBackArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(CheckoutPenarikanBuyerProses.this,FormPenarikanBuyer.class);
                startActivity(intent);
                progressBarGambung.startProgressBarGambung();
            }
        });
        progressBarGambung.endProgressBarGambung();
    }

    public void btnHome(View view) {
        finish();
        Intent intent = new Intent(CheckoutPenarikanBuyerProses.this,homeActivity.class);
        startActivity(intent);
        progressBarGambung.startProgressBarGambung();
    }

    public void btnKonfirmasipenarikan(View view) {
        showDialog();
    }

    public void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set title
        alertDialogBuilder.setTitle("Anda Yakin Untuk Mengakhiri Transaksi?");
        // set pesan
        alertDialogBuilder
                .setMessage("Klik Ya Jika Transaksi Selesai!")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // pindah activity
                        Intent intent = new Intent(CheckoutPenarikanBuyerProses.this,NotifikasiBerhasil.class);
                        startActivity(intent);
                        progressBarGambung.startProgressBarGambung();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        //builder
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void getXml() {
        this.mJicashValue = findViewById(R.id.JicashValue);
        this.mNoreqValue = findViewById(R.id.NoreqValue);
        this.mAtasnamaValue = findViewById(R.id.AtasnamaValue);
        this.mPenyediajasaValue = findViewById(R.id.PenyediajasaValue);}

    //public void uploadPenarikanJicash(){
        //Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        //Call<ResponseBody> uploadPenarikanJicash = service.uploadPenarikanJicash(
                //this.mJicashValue.getText().toString(),
                //this.mNoreqValue.getText().toString(),
                //this.mAtasnamaValue.getText().toString(),
                //this.mPenyediajasaValue.getText().toString()
        //);
        //uploadPenarikanJicash.enqueue(new Callback<ResponseBody>() {
            //@Override
            //public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //Toast.makeText(getApplicationContext(), "Bissmillah", Toast.LENGTH_SHORT).show();
            //}

            //@Override
            //public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Log.d(TAG, "onFailure: " + t.toString());
                //Toast.makeText(getApplicationContext(), "Bissmillah", Toast.LENGTH_SHORT).show();
            //}
        //});

}
//}
