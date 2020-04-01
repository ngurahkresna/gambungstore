package com.example.gambungstore.models.transaction;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Transaction {

    @SerializedName("data")
    List<DataTransaction> transactions;

    public List<DataTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<DataTransaction> transactions) {
        this.transactions = transactions;
    }
}
