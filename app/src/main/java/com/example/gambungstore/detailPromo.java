package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gambungstore.models.promo.DataPromo;

import org.w3c.dom.Text;

public class detailPromo extends AppCompatActivity {

    TextView mCodePromo,mDetailPromo,mSyaratPromo,mVoucherDate;
    Button mBtnCopy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_promo);

        mCodePromo = findViewById(R.id.tokenCode);
        mDetailPromo = findViewById(R.id.DetailPromo);
        mSyaratPromo = findViewById(R.id.SyaratPromo);
        mVoucherDate = findViewById(R.id.tanggalPromo);
        mBtnCopy = findViewById(R.id.btn_copy);

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

    public void copyCode(View view) {
        String getCode = mCodePromo.getText().toString();
        CharSequence text = "Code Promo " + getCode +" Tersalin";

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        String copy = mCodePromo.getText().toString();

        if (!copy.equalsIgnoreCase("")){
            ClipData clipdata = ClipData.newPlainText("text_copied", copy);
            clipboard.setPrimaryClip(clipdata);
        }

        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }
}
