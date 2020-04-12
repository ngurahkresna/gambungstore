package com.example.gambungstore.models.transaction;

import com.example.gambungstore.models.product.DataProduct;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class DetailTransaction {

    @SerializedName("code")
    private String code;
    @SerializedName("payment")
    private Payment payment;
    @SerializedName("shipping_charges")
    private int shipping_charges;
    @SerializedName("total_amount")
    private int total_amount;
    @SerializedName("discount_amount")
    private int discount_amount;
    @SerializedName("grand_total_amount")
    private int grand_total_amount;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("history")
    private List<History> history;

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(List<History> history) {
        this.history = history;
    }

    public String  getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String  created_at) {
        this.created_at = created_at;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getShipping_charges() {
        return shipping_charges;
    }

    public void setShipping_charges(int shipping_charges) {
        this.shipping_charges = shipping_charges;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public int getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(int discount_amount) {
        this.discount_amount = discount_amount;
    }

    public int getGrand_total_amount() {
        return grand_total_amount;
    }

    public void setGrand_total_amount(int grand_total_amount) {
        this.grand_total_amount = grand_total_amount;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
