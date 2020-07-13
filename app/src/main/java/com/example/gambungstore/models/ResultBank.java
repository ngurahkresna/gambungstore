package com.example.gambungstore.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ResultBank {
    @SerializedName("id_bank")
    private int id_bank;
    @SerializedName("bank_code")
    private int bank_code;
    @SerializedName("bank_name")
    private String bank_name;
    @SerializedName("created_at")
    private Date created_at;
    @SerializedName("update_at")
    private Date update_at;

    public int getId_bank() {
        return id_bank;
    }

    public void setId_bank(int id_bank) {
        this.id_bank = id_bank;
    }

    public int getBank_code() {
        return bank_code;
    }

    public void setBank_code(int bank_code) {
        this.bank_code = bank_code;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(Date update_at) {
        this.update_at = update_at;
    }
}
