package com.example.gambungstore.models.transaction;

import com.example.gambungstore.models.product.DataProduct;
import com.google.gson.annotations.SerializedName;

public class DetailTransaction {

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
