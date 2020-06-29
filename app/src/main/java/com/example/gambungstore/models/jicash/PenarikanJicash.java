package com.example.gambungstore.models.jicash;

import com.google.gson.annotations.SerializedName;

public class PenarikanJicash {

    @SerializedName("id")
    private int id;
    @SerializedName("ji_cash_id")
    private int ji_cash_id;
    @SerializedName("transaction_type")
    private String transaction_type;
    @SerializedName("amount")
    private int amount;
    @SerializedName("penarikan_approved")
    private String isApproved;
    @SerializedName("created_at")
    private String date;

    public PenarikanJicash(int id, int ji_cash_id, String transaction_type, int amount, String date) {
        this.id = id;
        this.ji_cash_id = ji_cash_id;
        this.transaction_type = transaction_type;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
