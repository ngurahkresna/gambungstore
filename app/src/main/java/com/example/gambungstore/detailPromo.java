package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.gambungstore.models.promo.DataPromo;

import org.w3c.dom.Text;

public class detailPromo extends AppCompatActivity {

    TextView mCodePromo,mDetailPromo,mSyaratPromo,mVoucherDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_promo);

        mCodePromo = findViewById(R.id.tokenCode);
        mDetailPromo = findViewById(R.id.DetailPromo);
        mSyaratPromo = findViewById(R.id.SyaratPromo);
        mVoucherDate = findViewById(R.id.tanggalPromo);

        DataPromo promo = getIntent().getExtras().getParcelable("promo");
        mCodePromo.setText(promo.getCode());
        if (promo.getType().equals("VCRCB")){
            mDetailPromo.setText("Voucher cashback sebesar "+Integer.toString(promo.getPercentage())+"%");
        }else{
            mDetailPromo.setText("Voucher discount sebesar "+Integer.toString(promo.getPercentage())+"%");
        }

        mSyaratPromo.setText(promo.getTerms());
        mVoucherDate.setText(promo.getEnd_date());
    }
}
