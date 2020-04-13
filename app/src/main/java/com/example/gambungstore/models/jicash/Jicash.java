package com.example.gambungstore.models.jicash;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Jicash {

    @SerializedName("id")
    private int id;
    @SerializedName("username")
    private String username;
    @SerializedName("balance")
    private String balance;
    @SerializedName("history")
    private List<HistoryJicash> history;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public List<HistoryJicash> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryJicash> history) {
        this.history = history;
    }
}
