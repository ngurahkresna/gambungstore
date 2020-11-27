package com.gambungstore.buyer.models;

import com.google.gson.annotations.SerializedName;

public class BankAccount {
    @SerializedName("id")
    private int id;

    @SerializedName("bank_code")
    private String bank_code;

    @SerializedName("account_no")
    private String account_no;

    @SerializedName("account_name")
    private String account_name;

    @SerializedName("status")
    private String status;

    @SerializedName("bank")
    private Bank bank;

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

    public String getAccountNo() {
        return account_no;
    }

    public void setAccountNo(String account_no) {
        this.account_no = account_no;
    }

    public String getAccountName() {
        return account_name;
    }

    public void setAccountName(String account_name) {
        this.account_name = account_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
