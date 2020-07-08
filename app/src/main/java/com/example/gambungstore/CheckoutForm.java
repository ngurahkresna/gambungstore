package com.example.gambungstore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gambungstore.adapters.ExpeditionCheckoutAdapter;
import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.checkout.Checkout;
import com.example.gambungstore.models.checkout.PaymentMethod;
import com.example.gambungstore.models.jicash.Jicash;
import com.example.gambungstore.models.product.DataProduct;
import com.example.gambungstore.models.store.DataStore;
import com.example.gambungstore.models.transaction.DetailTransaction;
import com.example.gambungstore.models.voucher.Voucher;
import com.example.gambungstore.progressbar.ProgressBarGambung;
import com.example.gambungstore.services.Services;
import com.example.gambungstore.sharedpreference.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutForm extends AppCompatActivity {
    private static final String TAG = "CheckoutForm";

    ProgressBarGambung progressbar = new ProgressBarGambung(this);

    RecyclerView mCheckoutCard;

    TextView mDiscountPrice,mProductPrice, mTotalPrice,mExpeditionPrice;
    TextView mAddress, mPayment, mChangeAddress;
    EditText mPhone;
    EditText mPromo;
    Button mButtonCekVoucher;

    ArrayList<String> paymentMethod = new ArrayList<>();
    ArrayList<String> product_code = new ArrayList<>();

    //global variable
    public int productPrice = 0;
    public int voucherPrice = 0;
    public String voucherType = "";
    public int grandTotalPrice = 0;
    public int expeditionPrice = 0;
    public String metodePembayaran = "";

    //expedition array
    public int[] expeditionArray;
    public String[] messageArray;

    //checkout detail
    ArrayList<String> store_id = new ArrayList<>();
    String[] expeditionCode;
    List<PaymentMethod> paymentMethods;

    String transaction_code;
    String created_at;
    double jicash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_form);

        progressbar.startProgressBarGambung();

        mCheckoutCard = findViewById(R.id.checkoutCard);
        mPayment = findViewById(R.id.payment);
        mAddress = findViewById(R.id.addressValue);
        mPhone = findViewById(R.id.phoneValue);
        mPromo = findViewById(R.id.promoValue);
        mProductPrice = findViewById(R.id.productPrice);
        mButtonCekVoucher = findViewById(R.id.buttonCekVoucher);
        mDiscountPrice = findViewById(R.id.discountPrice);
        mTotalPrice = findViewById(R.id.totalPrice);
        mExpeditionPrice = findViewById(R.id.expeditionPrice);
        mChangeAddress = findViewById(R.id.ubahAddress);

        getData();
        getJicash();

        mButtonCekVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekVoucher();
            }
        });

        mChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAddress();
            }
        });
    }

    public void paymentMethod(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Metode Pembayaran");

        builder.setItems(paymentMethod.toArray(new String[0]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CheckoutForm.this, "Anda Memilih " + paymentMethod.get(which), Toast.LENGTH_SHORT).show();
                mPayment.setText(paymentMethod.get(which));
                metodePembayaran = paymentMethod.get(which);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void checkoutAdapter(List<DataStore> listStores) {
        ExpeditionCheckoutAdapter checkoutAdapter = new ExpeditionCheckoutAdapter(listStores,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mCheckoutCard.setLayoutManager(linearLayoutManager);
        mCheckoutCard.setAdapter(checkoutAdapter);
    }

    public void submitButton(View view) {

        for(int expeditionCost : expeditionArray){
            if (expeditionCost == 0){
                Toast.makeText(CheckoutForm.this, "Anda Belum Memilih Expedisi", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (productPrice == 0){
            Toast.makeText(CheckoutForm.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
        }else if (voucherPrice != 0 && voucherType == ""){
            Toast.makeText(CheckoutForm.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
        }else if (grandTotalPrice == 0){
            Toast.makeText(CheckoutForm.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
        }else if (expeditionPrice == 0){
            Toast.makeText(CheckoutForm.this, "Anda Belum Memilih Expedisi", Toast.LENGTH_SHORT).show();
        }else if (metodePembayaran.equals("")){
            Toast.makeText(CheckoutForm.this, "Anda Belum Memilih Metode Pembayaran", Toast.LENGTH_SHORT).show();
        }else{
            progressbar.startProgressBarGambung();
            processCheckout();
        }
    }

    public void backToHome(View view) {
        finish();
        Intent intent = new Intent(CheckoutForm.this,homeActivity.class);
        startActivity(intent);
    }

    public void getData(){
        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<Checkout> callCheckout = service.getCheckout(
                SharedPreference.getRegisteredUsername(this)
        );

        callCheckout.enqueue(new Callback<Checkout>() {
            @Override
            public void onResponse(Call<Checkout> call, Response<Checkout> response) {
                Log.d(TAG, "onResponse: "+response.raw());
                mAddress.setText(response.body().getUser().getAddress().toString());
                mPhone.setText(response.body().getUser().getPhone().toString());
                mProductPrice.setText("Rp "+Integer.toString(response.body().getPrice())+",-");
                productPrice = response.body().getPrice();
                checkoutAdapter(response.body().getStore());

                //get name payment method
                paymentMethods = response.body().getPayments();
                for(PaymentMethod method : paymentMethods){
                    paymentMethod.add(method.getName());
                }

                //get all data store id
                for(DataStore store : response.body().getStore()){
                    store_id.add(store.getCode());
                }

                //get all product code
                int count = 0;
                for(DataStore store : response.body().getStore()) {
                    for (DataProduct product : store.getProducts()) {
                        if (product.getCarts().size() != 0) {
                            for (int i = 0; i < product.getCarts().size(); i++) {
                                if (product.getCarts().get(i).getUsername().equals(SharedPreference.getRegisteredUsername(CheckoutForm.this))) {
                                    product_code.add(product.getCode());
                                    Log.d(TAG, "onResponse: " + product.getCode()+" "+count);
                                    count++;
                                }
                            }
                        }
                    }
                }

                expeditionArray = new int[response.body().getStore().size()];
                expeditionCode = new String[response.body().getStore().size()];
                messageArray = new String[count];

                defaultVoucher();
                mTotalPrice.setText("Rp "+grandTotalPrice+",-");

                progressbar.endProgressBarGambung();
            }

            @Override
            public void onFailure(Call<Checkout> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });

    }

    private void cekVoucher(){
        progressbar.startProgressBarGambung();
        String code = mPromo.getText().toString();
        if (!code.equals("")){
            Services service = Client.getClient(Client.BASE_URL).create(Services.class);
            Call<Voucher> callVoucher = service.getVoucher(
                code
            );
            callVoucher.enqueue(new Callback<Voucher>() {
                @Override
                public void onResponse(Call<Voucher> call, Response<Voucher> response) {
                    voucherPrice = response.body().getPercentage()*productPrice/100;
                    Log.d(TAG, "onResponse: "+response.body().getPercentage());
                    Log.d(TAG, "onResponse: "+voucherPrice);
                    if (response.body().getType() != null){
                        if (response.body().getType().equals("VCRCB")){
                            voucherType = "cashback";
                        }else{
                            voucherType = "discount";
                            grandTotalPrice -= voucherPrice;
                        }
                        alertDialogNotif("Berhasil Menambahkan Voucher");
                        refreshRincianHarga();
                    }else{
                        defaultVoucher();
                        refreshRincianHarga();
                        alertDialogNotif("Voucher Tidak Ditemukan");
                    }

                }

                @Override
                public void onFailure(Call<Voucher> call, Throwable t) {
                    Log.d(TAG, "onFailure: "+t.toString());
                    defaultVoucher();
                    refreshRincianHarga();
                    alertDialogNotif("Voucher Tidak Ditemukan");
                }
            });
        }else{
            defaultVoucher();
            refreshRincianHarga();
            alertDialogNotif("Anda Belum Memasukan Kode Voucher");
        }
        progressbar.endProgressBarGambung();

    }

    public void refreshRincianHarga(){
        if (voucherType.equals("cashback")){
            grandTotalPrice = productPrice + expeditionPrice;
            mDiscountPrice.setText("Rp "+Integer.toString(voucherPrice)+",- ("+voucherType.toUpperCase()+")");
        }else{
            grandTotalPrice = productPrice - voucherPrice + expeditionPrice;
            mDiscountPrice.setText("Rp "+Integer.toString(voucherPrice)+",-");
        }

        mTotalPrice.setText("Rp "+Integer.toString(grandTotalPrice)+",-");
        mExpeditionPrice.setText("Rp "+Integer.toString(expeditionPrice)+",-");
    }

    private void defaultVoucher(){
        voucherPrice = 0;
        voucherType = "";
        grandTotalPrice = productPrice - voucherPrice + expeditionPrice;
    }

    private void alertDialogNotif(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(CheckoutForm.this).create();
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void setExpedition(int cost, int position, String code){
        int totalExpedition = 0;
        expeditionArray[position] = cost;
        expeditionCode[position] = code;

        Log.d(TAG, "setExpedition: "+position);

        for (int value: expeditionArray) {
            totalExpedition += value;
        }

        this.expeditionPrice = totalExpedition;
    }

    private void processCheckout(){
        int temp = 0;
        for(PaymentMethod method : paymentMethods){
            if (method.getName().equals(metodePembayaran)){
                temp = method.getId();
            }
        }
        final int payment_id = temp;
        if (payment_id == 1){
            if (grandTotalPrice > jicash){
                Toast.makeText(this, "Saldo Jicash anda tidak cukup", Toast.LENGTH_SHORT).show();
                progressbar.endProgressBarGambung();
                return;
            }
        }

        Log.d(TAG, "processCheckout: "+payment_id+" "+metodePembayaran+" "+paymentMethods.get(0)+" "+paymentMethods.get(1));

        //parse array to array list
        ArrayList<String> expedition = new ArrayList<>();
        for(String exp : expeditionCode){
            expedition.add(exp);
            Log.d(TAG, "processCheckout: "+exp);
        }

        ArrayList<String> messages = new ArrayList<>();
        for (String mes : messageArray){
            messages.add(mes);
        }

        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<DetailTransaction> callCheckoutProcess = service.processCheckout(
            SharedPreference.getRegisteredUsername(this),
                mAddress.getText().toString(),
                mPhone.getText().toString(),
                this.store_id,
                expedition,
                expeditionPrice,
                productPrice,
                voucherPrice,
                grandTotalPrice,
                payment_id,
                product_code,
                messages,
                mPromo.getText().toString()
        );

        callCheckoutProcess.enqueue(new Callback<DetailTransaction>() {
            @Override
            public void onResponse(Call<DetailTransaction> call, Response<DetailTransaction> response) {
                Log.d(TAG, "onResponse proses: "+response.raw());
                Log.d(TAG, "onResponse proses: "+response.body().getCreated_at());
                Toast.makeText(CheckoutForm.this, "Berhasil Checkout", Toast.LENGTH_SHORT).show();
                transaction_code = response.body().getCode();
                created_at = response.body().getCreated_at();

                if (payment_id == 1){
                    Intent intent = new Intent(CheckoutForm.this, CheckoutDone.class);
                    intent.putExtra("payment","transfer");
                    finish();
                    startActivity(intent);
                    progressbar.endProgressBarGambung();
                    return;
                }

                Intent intent = new Intent(CheckoutForm.this, CheckoutPayment.class);
                intent.putExtra("flag","checkoutform");
                intent.putExtra("productPrice", productPrice);
                intent.putExtra("discountPrice", voucherPrice);
                intent.putExtra("expeditionPrice",expeditionPrice);
                intent.putExtra("grandTotalPrice",grandTotalPrice);
                intent.putExtra("transaction_code", transaction_code);
                intent.putExtra("discountType", voucherType);
                intent.putExtra("created_at",response.body().getCreated_at());
                finish();
                startActivity(intent);


                progressbar.endProgressBarGambung();
            }

            @Override
            public void onFailure(Call<DetailTransaction> call, Throwable t) {
                Toast.makeText(CheckoutForm.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                progressbar.endProgressBarGambung();
            }
        });
    }

    private void changeAddress(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ubah Alamat");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAddress.setText(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void getMessage(String text, int position){
        messageArray[position] = text;

        Log.d(TAG, "setMessage: "+position);
        Log.d(TAG, "getMessage: "+text);
    }

    private void getJicash(){

        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<List<Jicash>> jicashCall = service.getJicash(
                SharedPreference.getRegisteredUsername(this)
        );
        jicashCall.enqueue(new Callback<List<Jicash>>() {
            @Override
            public void onResponse(Call<List<Jicash>> call, Response<List<Jicash>> response) {
                if(response.body().size()!=0) {

                    jicash = Double.valueOf(response.body().get(0).getBalance());
                }
                else{
                    jicash = 0;
                }
            }

            @Override
            public void onFailure(Call<List<Jicash>> call, Throwable t) {
                Toast.makeText(CheckoutForm.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                progressbar.endProgressBarGambung();
            }
        });
    }
}
