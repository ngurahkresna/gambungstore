package com.example.gambungstore.models.jicash;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PenarikanJicash {

    @SerializedName("id")
    private int id;
    @SerializedName("jumlah_jicash")
    private int jumlah_jicash;
    @SerializedName("no_rekening")
    private int no_rekening;
    @SerializedName("atas_nama")
    private String atas_nama;
    @SerializedName("penyedia_jasa")
    private String penyedia_jasa;
    //
    @SerializedName("username")
    private String  username;
    @SerializedName("amount")
    private int amount;
    @SerializedName("account_no")
    private int account_no;
    @SerializedName("account_name")
    private String account_name;
    @SerializedName("bank_code")
    private String bank_code;

    //umi
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAccount_no() {
        return account_no;
    }

    public void setAccount_no(int account_no) {
        this.account_no = account_no;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }
    //

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJumlah_jicash() {
        return jumlah_jicash;
    }

    public void setJumlah_jicash(int jumlah_jicash) {
        this.jumlah_jicash = jumlah_jicash;
    }

    public int getNo_rekening() {
        return no_rekening;
    }

    public void setNo_rekening(int no_rekening) {
        this.no_rekening = no_rekening;
    }

    public String getAtas_nama() {
        return atas_nama;
    }

    public void setAtas_nama(String atas_nama) {
        this.atas_nama = atas_nama;
    }

    public String getPenyedia_jasa() {
        return penyedia_jasa;
    }

    public void setPenyedia_jasa(String penyedia_jasa) {
        this.penyedia_jasa = penyedia_jasa;
    }
}
