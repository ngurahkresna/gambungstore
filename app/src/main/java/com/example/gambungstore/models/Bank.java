package com.example.gambungstore.models;

import com.example.gambungstore.models.product.DataProduct;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Bank {
    @SerializedName("id")
    private int id;
    @SerializedName("bank_code")
    private String bank_code;
    @SerializedName("bank_name")
    private String bank_name;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("update_at")
    private String update_at;
    @SerializedName("data")
    private List<Bank> Bank;

    public List<Bank> getBank() {
        return Bank;
    }

    public void setBank(List<Bank> bank) {
        Bank = bank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }
}
