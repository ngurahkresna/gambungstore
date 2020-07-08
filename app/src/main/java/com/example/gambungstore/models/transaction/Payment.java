package com.example.gambungstore.models.transaction;

import com.google.gson.annotations.SerializedName;

public class Payment {

    @SerializedName("status_upload")
    private String status_uplaod;
    @SerializedName("verified_status")
    private String verified_status;
    @SerializedName("updated_process")
    private String updated_process;
    @SerializedName("payment_method_id")
    private int payment_method_id;
    @SerializedName("verified_date")
    private String verified_date;
    @SerializedName("account_number")
    private String account_number;
    @SerializedName("account_name")
    private String account_name;
    @SerializedName("account_bank")
    private String account_bank;

    public String getAccount_number() {
        return account_number;
    }

    public String getAccount_name() {
        return account_name;
    }

    public String getAccount_bank() {
        return account_bank;
    }

    public String getStatus_uplaod() {
        return status_uplaod;
    }

    public void setStatus_uplaod(String status_uplaod) {
        this.status_uplaod = status_uplaod;
    }

    public String getVerified_status() {
        return verified_status;
    }

    public void setVerified_status(String verified_status) {
        this.verified_status = verified_status;
    }

    public String getUpdated_process() {
        return updated_process;
    }

    public void setUpdated_process(String updated_process) {
        this.updated_process = updated_process;
    }

    public int getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(int payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    public String getVerified_date() {
        return verified_date;
    }

    public void setVerified_date(String verified_date) {
        this.verified_date = verified_date;
    }
}

