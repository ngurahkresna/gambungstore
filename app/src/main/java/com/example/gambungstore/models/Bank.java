package com.example.gambungstore.models;

import com.google.gson.annotations.SerializedName;

public class Bank {
    @SerializedName("id")
    private int id;

    @SerializedName("bank_code")
    private String bank_code;

    @SerializedName("bank_name")
    private String bank_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBankCode() {
        return bank_code;
    }

    public void setBankCode(String bank_code) {
        this.bank_code = bank_code;
    }

    public String getBankName() {
        return bank_name;
    }

    public void setBankName(String bank_name) {
        this.bank_name = bank_name;
    }
}
