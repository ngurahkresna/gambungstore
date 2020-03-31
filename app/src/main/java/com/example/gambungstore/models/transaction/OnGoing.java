package com.example.gambungstore.models.transaction;

import com.example.gambungstore.R;

import java.util.ArrayList;
import java.util.List;

public class OnGoing {
    private List<DataOnGoing> dataOnGoing;
    private static int[] foto = {
            R.drawable.foto_produk,
            R.drawable.foto_produk,
            R.drawable.foto_produk
    };
    private static String[] tanggal = {
            "15 Januari 2020",
            "16 Februari 2020",
            "17 Maret 2020"
    };
    private static String[] produk = {
            "Zazeim BoBa",
            "GeMay Tea",
            "YCookie"
    };
    private static int[] harga = {
            10000,
            20000,
            30000
    };
    private static int[] qty = {
            1,
            2,
            3
    };
    private static String[] status = {
            "Sedang Dikirim",
            "Terkirim",
            "Menunggu Konfirmasi Pembayaran"
    };
    private static String[] invoice = {
            "Invoice 1",
            "Invoice 2",
            "Invoice 3"
    };
    private static int[] courier = {
            R.drawable.courier_image,
            R.drawable.courier_image,
            R.drawable.courier_image
    };

    
    public List<DataOnGoing> getDataOnGoing() {
            ArrayList<DataOnGoing> list = new ArrayList<>();
            for (int position = 0; position < produk.length; position++){
                DataOnGoing dataOnGoing = new DataOnGoing();
                dataOnGoing.setFoto(foto[position]);
                dataOnGoing.setTanggal(tanggal[position]);
                dataOnGoing.setProduk(produk[position]);
                dataOnGoing.setHarga(harga[position]);
                dataOnGoing.setQty(qty[position]);
                dataOnGoing.setStatus(status[position]);
                dataOnGoing.setInvoice(invoice[position]);
                dataOnGoing.setCourier(courier[position]);
                list.add(dataOnGoing);
            }
            return list;
        }

    public void setDataOnGoing(List<DataOnGoing> dataOnGoing) {
        this.dataOnGoing = dataOnGoing;
    }
}


