package com.gambungstore.buyer.models.jicash;

import com.google.gson.annotations.SerializedName;

public class HistoryJicash {

    @SerializedName("id")
    private int id;
    @SerializedName("ji_cash_id")
    private int ji_cash_id;
    @SerializedName("transaction_type")
    private String transaction_type;
    @SerializedName("amount")
    private int amount;
    @SerializedName("is_topup_approved")
    private String isApproved;
    @SerializedName("topup_proof_image")
    private String topup_image;
    @SerializedName("created_at")
    private String date;

    public HistoryJicash(int id, int ji_cash_id, String transaction_type, int amount, String date) {
        this.id = id;
        this.ji_cash_id = ji_cash_id;
        this.transaction_type = transaction_type;
        this.amount = amount;
        this.date = date;
    }

    public String getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(String isApproved) {
        this.isApproved = isApproved;
    }

    public String getTopup_image() {
        return topup_image;
    }

    public void setTopup_image(String topup_image) {
        this.topup_image = topup_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJi_cash_id() {
        return ji_cash_id;
    }

    public void setJi_cash_id(int ji_cash_id) {
        this.ji_cash_id = ji_cash_id;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
